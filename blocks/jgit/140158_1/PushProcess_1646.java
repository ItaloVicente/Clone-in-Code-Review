
package org.eclipse.jgit.transport;

import java.io.OutputStream;
import java.util.Map;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.ProgressMonitor;

public interface PushConnection extends Connection {

	void push(final ProgressMonitor monitor
			final Map<String
			throws TransportException;

	void push(final ProgressMonitor monitor
			final Map<String
			throws TransportException;

}
