package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.URIish;

public class ServiceNotPermittedException extends TransportException {
	private static final long serialVersionUID = 1L;

	private String service;

	public ServiceNotPermittedException(URIish uri
		super(uri
		this.service = service;
	}

	public String getService() {
		return service;
	}
}
