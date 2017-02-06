package com.semperti.hipotecario.poc.fuse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.semperti.hipotecario.poc.fuse.model.Persona;
import com.semperti.hipotecario.poc.fuse.model.Personas;
import com.semperti.hipotecario.poc.fuse.model.Telefono;
import com.semperti.hipotecario.poc.fuse.model.Telefonos;

import java.util.Iterator;

public class ServiceAggregator implements AggregationStrategy {
	private static final Logger logger = LoggerFactory.getLogger(ServiceAggregator.class);

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		if (oldExchange == null)
			return newExchange;
		if (newExchange == null)
			return oldExchange;

		if (newExchange.getIn().getHeader("SERVICE_NAME", String.class).equals("COBIS"))
			return performAggregate(newExchange, oldExchange);

		return performAggregate(oldExchange, newExchange);
	}

	/** Cobis gana a Bup **/
	protected Exchange performAggregate(Exchange cobisExchange, Exchange bupExchange) {
		String operationName = cobisExchange.getIn().getHeader("operationName", String.class);
		Exchange returnExchange = cobisExchange;
		switch(operationName) {
			case "obtenerPersonas":
				aggregateObtenerPersonas(cobisExchange, bupExchange);
				break;

			case "obtenerPersona":
				fillPersona(cobisExchange.getIn().getBody(Persona.class), bupExchange.getIn().getBody(Persona.class));
				break;

			case "obtenerTelefonos":
				aggregateObtenerTelefonos(cobisExchange, bupExchange);
				break;

			case "obtenerTelefonosDePersona":
				aggregateObtenerTelefonos(cobisExchange, bupExchange);
				break;

			case "obtenerTelefono":
				fillTelefono(cobisExchange.getIn().getBody(Telefono.class), bupExchange.getIn().getBody(Telefono.class));
				break;

			case "obtenerPrestamoPatrimonial":
			case "obtenerPrestamosPatrimoniales":
			case "obtenerPrestamosPatrimonialesDePersona":
			case "obtenerTarjetaCreditoPatrimonial":
			case "obtenerTarjetasCreditoPatrimoniales":
			case "obtenerTarjetasCreditoPatrimonialesDePersona":
				break;

			case "obtenerDomicilio":
			case "obtenerDomicilios":
			case "obtenerDomiciliosDePersona":
				returnExchange = bupExchange;
				break;

			default:
				logger.error("Operation: '{}' desconocida", operationName);
		}

		return returnExchange;
	}

	protected void aggregateObtenerPersonas(Exchange cobisExchange, Exchange bupExchange) {
		Personas cobisPersonas = cobisExchange.getIn().getBody(Personas.class);
		Personas bupPersonas = bupExchange.getIn().getBody(Personas.class);

		for (Persona cobisPersona : cobisPersonas.getPersonas()) {
			for (Iterator<Persona> it = bupPersonas.getPersonas().iterator(); it.hasNext(); ) {
				Persona bupPersona = it.next();
				if (!cobisPersona.getId().equals(bupPersona.getId()))
					continue;

				fillPersona(cobisPersona, bupPersona);
				it.remove();
				break;
			}
		}

		// XXX: Se podrian agregar las peronas que sobraron
	}

	protected void fillPersona(Persona cobisPersona, Persona bupPersona) {
		cobisPersona.setEsPersonaFisica(bupPersona.getEsPersonaFisica());
		cobisPersona.setEsPersonaJuridica(bupPersona.getEsPersonaJuridica());
		cobisPersona.setValorLealtadCliente(bupPersona.getValorLealtadCliente());
	}

	protected void aggregateObtenerTelefonos(Exchange cobisExchange, Exchange bupExchange) {
		Telefonos cobisTelefonos = cobisExchange.getIn().getBody(Telefonos.class);
		Telefonos bupTelefonos = bupExchange.getIn().getBody(Telefonos.class);

		for (Telefono cobisTelefono : cobisTelefonos.getTelefonos()) {
			for (Iterator<Telefono> it = bupTelefonos.getTelefonos().iterator(); it.hasNext(); ) {
				Telefono bupTelefono = it.next();
				if (!cobisTelefono.getId().equals(bupTelefono.getId()))
					continue;

				fillTelefono(cobisTelefono, bupTelefono);
				it.remove();
				break;
			}
		}

		// XXX: Se podrian agregar las telefonos que sobraron
	}

	protected void fillTelefono(Telefono cobisTelefono, Telefono bupTelefono) {
		cobisTelefono.setPrioridad(bupTelefono.getPrioridad());
		cobisTelefono.setEsListaNegra(bupTelefono.getEsListaNegra());
	}
}
