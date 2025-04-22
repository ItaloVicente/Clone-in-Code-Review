
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.TransportException;

public interface SubscribeConnection extends Connection {
	void sendSubscribeAdvertisement(SubscriptionState subscriber)
			throws IOException

	void subscribe(SubscriptionState subscriber
			Map<String
			PrintWriter output)
			throws InterruptedException
}
