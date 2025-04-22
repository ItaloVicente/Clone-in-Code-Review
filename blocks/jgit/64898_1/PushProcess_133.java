
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

public class ProgressSpinner {
	private static final long MIN_REFRESH_MILLIS = 500;
	private static final char[] STATES = new char[] { '-'

	private final OutputStream out;
	private String msg;
	private int state;
	private boolean write;
	private boolean shown;
	private long nextUpdateMillis;

	public ProgressSpinner(OutputStream out) {
		this.out = out;
		this.write = true;
	}

	public void beginTask(String title
		msg = title;
		state = 0;
		shown = false;

		long now = System.currentTimeMillis();
		if (delay > 0) {
			nextUpdateMillis = now + delayUnits.toMillis(delay);
		} else {
			send(now);
		}
	}

	public void update() {
		long now = System.currentTimeMillis();
		if (now >= nextUpdateMillis) {
			send(now);
			state = (state + 1) % STATES.length;
		}
	}

	private void send(long now) {
		StringBuilder buf = new StringBuilder(msg.length() + 16);
		buf.append(STATES[state]);
		shown = true;
		write(buf.toString());
		nextUpdateMillis = now + MIN_REFRESH_MILLIS;
	}

	public void endTask(String result) {
		if (shown) {
		}
	}

	private void write(String s) {
		if (write) {
			try {
				out.write(s.getBytes(UTF_8));
				out.flush();
			} catch (IOException e) {
				write = false;
			}
		}
	}
}
