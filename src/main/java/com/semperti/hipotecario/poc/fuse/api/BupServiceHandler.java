package com.semperti.hipotecario.poc.fuse.api;

import org.apache.camel.Exchange;

import org.apache.camel.component.cxf.common.message.CxfConstants;

import com.semperti.hipotecario.poc.fuse.model.Persona;
import com.semperti.hipotecario.poc.fuse.model.Personas;
import com.semperti.hipotecario.poc.fuse.model.Telefono;
import com.semperti.hipotecario.poc.fuse.model.Telefonos;
import com.semperti.hipotecario.poc.fuse.model.Domicilio;
import com.semperti.hipotecario.poc.fuse.model.Domicilios;

public class BupServiceHandler {
	public void obtenerDomicilios(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Domicilios.class);
	}

	public void obtenerDomicilio(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Domicilio.class);
	}

	public void obtenerPersonas(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Personas.class);
	}

	public void obtenerDomiciliosDePersona(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Domicilios.class);
	}

	public void obtenerPersona(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Persona.class);
	}

	public void obtenerTelefonosDePersona(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Telefonos.class);
	}

	public void obtenerTelefonos(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Telefonos.class);
	}

	public void obtenerTelefono(Exchange exchange) {
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_USING_HTTP_API, Boolean.TRUE);
		exchange.getIn().setHeader(CxfConstants.CAMEL_CXF_RS_RESPONSE_CLASS, Telefono.class);
	}
}
