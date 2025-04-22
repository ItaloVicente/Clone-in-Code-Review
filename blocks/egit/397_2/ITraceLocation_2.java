package org.eclipse.egit.core.trace;

import org.eclipse.egit.core.Activator;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugTrace;

public enum GitTraceLocation implements ITraceLocation {
	CORE("/debug/core"), //$NON-NLS-1$
	UI("/debug/ui"); //$NON-NLS-1$

	public static void initializeFromOptions(DebugOptions options, boolean pluginIsDebugging) {

		if (pluginIsDebugging) {
			myTrace = options.newDebugTrace(Activator.getPluginId());
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
