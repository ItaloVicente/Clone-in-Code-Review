
package org.eclipse.jgit.http.server.resolver;

public class ServiceNotEnabledException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceNotEnabledException() {
		super("Service not enabled");
	}
}
