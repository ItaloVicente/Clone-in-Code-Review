
package org.eclipse.jgit.transport;

public abstract class RemoteCommandConnectionFactory {
	public abstract RemoteCommandConnection getConnection(
			TransportGitSsh transportGitSsh);
}
