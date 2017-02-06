package com.semperti.hipotecario.poc.fuse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.camel.Exchange;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.NotAuthorizedException;

import com.semperti.hipotecario.poc.fuse.model.Error;

public class ExceptionHandler implements ExceptionMapper<Exception> {
	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

	private String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}

	public Response toResponse(Exception cause) {
		int status = 500;
		String errorMsg = "Internal server Error";

		logger.info("Exception de tipo: {}", cause.getClass());

		if (cause.getCause() != null) {
			logger.info("Causa: {}", cause.getCause());
		}

		logger.error("Exception: {}", cause);
		logger.error(getStackTrace(cause));

		return Response.status(status).entity(buildErrorResponse(cause, status, errorMsg)).build();
	}

	private Error buildErrorResponse(Exception cause, int status, String errorMsg) {
		Error error = new Error();

		error.setMensajeAlDesarrollador(cause.getMessage());
		error.setMensajeAlUsuario(errorMsg);
		error.setCodigo(status);
		error.setDetalle(cause.toString());
		error.setMasInformacion(getStackTrace(cause));

		return error;
	}
}
