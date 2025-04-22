
package org.eclipse.jgit.junit.http;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jetty.util.log.Logger;

public class RecordingLogger implements Logger {
	private static List<Warning> warnings = new ArrayList<>();

	public static void clear() {
		synchronized (warnings) {
			warnings.clear();
		}
	}

	public static List<Warning> getWarnings() {
		synchronized (warnings) {
			ArrayList<Warning> copy = new ArrayList<>(warnings);
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

		public Warning(Throwable thrown) {
			super(thrown);
		}
	}

	private final String name;

	public RecordingLogger() {
		this("");
	}

	public RecordingLogger(String name) {
		this.name = name;
	}

	@Override
	public Logger getLogger(@SuppressWarnings("hiding") String name) {
		return new RecordingLogger(name);
	}

	@Override
	public String getName() {
		return name;
	}

	public void warn(String msg
		synchronized (warnings) {
			warnings.add(new Warning(MessageFormat.format(msg
		}
	}

	@Override
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

	@Override
	public void debug(String msg
	}

	public void debug(String msg) {
	}

	public void info(String msg
	}

	public void info(String msg) {
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public void setDebugEnabled(boolean enabled) {
	}

	@Override
	public void warn(String msg
		synchronized (warnings) {
			int i = 0;
			int index = msg.indexOf("{}");
			while (index >= 0) {
				msg = msg.replaceFirst("\\{\\}"
				index = msg.indexOf("{}");
			}
			warnings.add(new Warning(MessageFormat.format(msg
		}
	}

	@Override
	public void warn(Throwable thrown) {
		synchronized (warnings) {
			warnings.add(new Warning(thrown));
		}
	}

	@Override
	public void info(String msg
	}

	@Override
	public void info(Throwable thrown) {
	}

	@Override
	public void info(String msg
	}

	@Override
	public void debug(String msg
	}

	@Override
	public void debug(Throwable thrown) {
	}

	@Override
	public void ignore(Throwable arg0) {
	}

	@Override
	public void debug(String msg
	}
}
