
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.transport.PubSubConfig.Publisher;

class SubscribeProcess {
	private static class Backoff {
		int n;

		final int max;

		final int timeout;

		long lastCall;

		Backoff(int max
			this.max = max;
			this.timeout = timeout;
		}

		void backoff() throws InterruptedException {
			if (milliTime() - lastCall > timeout)
				reset();
			n += 1;
			double r = Math.random() + 0.5;
			double exp = Math.pow(2
			long delay = (long) (r * timeout * exp);
			Thread.sleep(Math.min(max
			lastCall = milliTime();
		}

		void reset() {
			n = 0;
		}

		long milliTime() {
			return System.nanoTime() / 1000000;
		}
	}

	private final Subscriber subscriber;

	private Thread subscriberThread;

	public SubscribeProcess(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public void execute(Map<String
			Publisher publisher
			throws NotSupportedException
			URISyntaxException {
		Backoff backoff = new Backoff(240 * 1000
		while (true) {
			if (Thread.interrupted())
				throw new InterruptedException();
			try {
				subscriber.subscribe(commands
			} catch (NotSupportedException e) {
				throw e;
			} catch (TransportException e) {
				throw e;
			} catch (IOException e) {
				if (subscriber.getRestartToken() != null)
					commands = Collections.emptyMap();
				backoff.backoff();
				continue;
			}
			backoff.reset();
			reset();
			commands = subscriber.sync(publisher);
		}
	}

	public void reset() {
		List<RefSpec> clearSpecs = Collections.emptyList();
		for (String name : subscriber.getAllRepositories()) {
			SubscribedRepository sr = subscriber.getRepository(name);
			if (sr != null)
				sr.setSubscribeSpecs(clearSpecs);
		}
		subscriber.setRestartToken(null);
		subscriber.setLastPackNumber(null);
	}

	public Map<String
			throws IOException
		return subscriber.sync(p);
	}

	public void setThread(Thread thread) {
		subscriberThread = thread;
	}

	public void close() {
		if (subscriber == null)
			return;
		subscriber.close();
		if (subscriberThread != null)
			subscriberThread.interrupt();
	}
}
