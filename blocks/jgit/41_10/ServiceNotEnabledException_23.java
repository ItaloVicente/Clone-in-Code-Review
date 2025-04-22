
package org.eclipse.jgit.http.server.resolver;

public class ServiceNotAuthorizedException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceNotAuthorizedException() {
		super("Service not permitted");
	}
}
