
package org.eclipse.jgit.http.test.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jetty.util.log.Logger;

public class RecordingLogger implements Logger {
	private static List<Warning> warnings = new ArrayList<Warning>();

	public static void clear() {
		synchronized (warnings) {
			warnings.clear();
		}
	}

	public static List<Warning> getWarnings() {
		synchronized (warnings) {
			ArrayList<Warning> copy = new ArrayList<Warning>(warnings);
			return Collections.unmodifiableList(copy);
		}
	}

	@SuppressWarnings("serial")
	public static class Warning extends Exception {
		public Warning(String msg) {
			super(msg);
		}

		public Warning(String msg
			super(msg
		}
	}

	private final String name;

	public RecordingLogger() {
		this("");
	}

	public RecordingLogger(final String name) {
		this.name = name;
	}

	public Logger getLogger(@SuppressWarnings("hiding") String name) {
		return new RecordingLogger(name);
	}

	public String getName() {
		return name;
	}

	public void warn(String msg
		synchronized (warnings) {
			warnings.add(new Warning(MessageFormat.format(msg
		}
	}

	public void warn(String msg
		synchronized (warnings) {
			warnings.add(new Warning(msg
		}
	}

	public void warn(String msg) {
		synchronized (warnings) {
			warnings.add(new Warning(msg));
		}
	}

	public void debug(String msg
	}

	public void debug(String msg
	}

	public void debug(String msg) {
	}

	public void info(String msg
	}

	public void info(String msg) {
	}

	public boolean isDebugEnabled() {
		return false;
	}

	public void setDebugEnabled(boolean enabled) {
	}
}
