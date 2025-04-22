package org.eclipse.ui.internal.monitoring;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.Platform;

public class Tracer {
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss.SSS"); //$NON-NLS-1$
	private final Date date = new Date();
	private final String prefix;
	private final PrintStream out = System.out;

	public static boolean isTracingEnabled(String debugOption) {
		return Platform.isRunning() && Boolean.parseBoolean(Platform.getDebugOption(debugOption));
	}

	public static Tracer create(String prefix, String debugOption) {
		if (isTracingEnabled(debugOption)) {
			return new Tracer(prefix);
		}
		return null;
	}

	protected Tracer(String prefix) {
		this.prefix = prefix;
	}

	public void trace(Object o) {
		out.printf("%s %s: %s\n", getTimestamp(), prefix, o); //$NON-NLS-1$
	}

	public void trace(String msg, Object... params) {
		trace(String.format(msg, params));
	}

	public void traceStackTrace(Throwable t) {
		StringWriter writer = new StringWriter();
		PrintWriter printer = new PrintWriter(writer);
		t.printStackTrace(printer);
		printer.flush();
		trace(writer.toString());
	}

	private String getTimestamp() {
		date.setTime(System.currentTimeMillis());
		return timeFormatter.format(date);
	}
}
