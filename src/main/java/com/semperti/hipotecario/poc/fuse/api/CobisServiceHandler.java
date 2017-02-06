package com.semperti.hipotecario.poc.fuse.api;

import org.apache.camel.Exchange;

import org.apache.camel.component.cxf.common.message.CxfConstants;

import com.semperti.hipotecario.poc.fuse.model.Persona;
import com.semperti.hipotecario.poc.fuse.model.Personas;
import com.semperti.hipotecario.poc.fuse.model.Telefono;
import com.semperti.hipotecario.poc.fuse.model.Telefonos;
import com.semperti.hipotecario.poc.fuse.model.PrestamoPatrimonial;
import com.semperti.hipotecario.poc.fuse.model.PrestamosPatrimoniales;
import com.semperti.hipotecario.poc.fuse.model.TarjetaCreditoPatrimonial;
import com.semperti.hipotecario.poc.fuse.model.TarjetasCreditoPatrimoniales;

public class CobisServiceHandler {
	public void obtenerPersonas(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Personas.class);
	}

	public void obtenerPersona(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Persona.class);
	}

	public void obtenerTelefonosDePersona(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Telefonos.class);
	}

	public void obtenerTarjetasCreditoPatrimonialesDePersona(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, TarjetasCreditoPatrimoniales.class);
	}

	public void obtenerPrestamosPatrimonialesDePersona(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, PrestamosPatrimoniales.class);
	}

	public void obtenerTelefonos(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Telefonos.class);
	}

	public void obtenerTelefono(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Telefono.class);
	}

	public void obtenerTarjetasCreditoPatrimoniales(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, TarjetasCreditoPatrimoniales.class);
	}

	public void obtenerTarjetaCreditoPatrimonial(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, TarjetaCreditoPatrimonial.class);
	}

	public void obtenerPrestamosPatrimoniales(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, PrestamosPatrimoniales.class);
	}

	public void obtenerPrestamoPatrimonial(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, PrestamoPatrimonial.class);
	}
}
