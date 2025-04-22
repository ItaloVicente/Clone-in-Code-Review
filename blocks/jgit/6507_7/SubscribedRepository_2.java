
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.ProgressMonitor;

public interface SubscribeConnection extends Connection {
	void doSubscribe(Subscriber subscriber
			Map<String
			ProgressMonitor monitor)
			throws InterruptedException
}
