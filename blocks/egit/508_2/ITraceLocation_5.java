package org.eclipse.egit.ui.nternal.trace;

import org.eclipse.egit.ui.Activator;
import org.eclipse.osgi.service.debug.DebugOptions;

public enum GitTraceLocation implements ITraceLocation {

	UI("/debug/ui"); //$NON-NLS-1$

	public static void initializeFromOptions(DebugOptions options, boolean pluginIsDebugging) {

		if (pluginIsDebugging) {
			myTrace = new DebugTrace() {

				public void traceEntry(String location, String message) {
				}

				public void traceEntry(String location) {
				}

				public void trace(String location, String message, Throwable error) {
					System.out.println(message);
					if (error != null)
						System.out.println(error.getMessage());

				}

				public void trace(String location, String message) {
					System.out.println(message);
				}
			};

			for (GitTraceLocation loc : values()) {
				boolean active = options.getBooleanOption(loc.getFullPath(), false);
				loc.setActive(active);
			}
		} else {
			for (GitTraceLocation loc : values()) {
				loc.setActive(false);
			}
		}
	}

	private final String location;
	private final String fullPath;

	private boolean active = false;
	private static DebugTrace myTrace;

	private GitTraceLocation(String path) {
		this.fullPath = Activator.getPluginId() + path;
		this.location = path;
	}

	public static DebugTrace getTrace() {
		return GitTraceLocation.myTrace;
	}

	public boolean isActive() {
		return this.active;
	}

	public String getFullPath() {
		return this.fullPath;
	}

	public String getLocation() {
		return this.location;
	}

	private void setActive(boolean active) {
		this.active = active;
	}

}
