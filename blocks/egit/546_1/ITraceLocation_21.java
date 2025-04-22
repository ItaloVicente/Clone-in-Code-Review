package org.eclipse.egit.ui.internal.trace;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.egit.ui.Activator;
import org.eclipse.osgi.service.debug.DebugOptions;

public enum GitTraceLocation implements ITraceLocation {

	UI("/debug/ui"); //$NON-NLS-1$

	public static void initializeFromOptions(DebugOptions options,
			boolean pluginIsDebugging) {

		if (pluginIsDebugging) {
			myTrace = new DebugTraceImpl();

			for (GitTraceLocation loc : values()) {
				boolean active = options.getBooleanOption(loc.getFullPath(),
						false);
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

	private static final class DebugTraceImpl implements DebugTrace {

		private ILog myLog;

		public void trace(String location, String message) {
			getLog().log(
					new Status(IStatus.INFO, Activator.getPluginId(), message));

		}

		public void trace(String location, String message, Throwable error) {

			getLog().log(
					new Status(IStatus.INFO, Activator.getPluginId(), message));
			if (error != null)
				getLog().log(
						new Status(IStatus.INFO, Activator.getPluginId(), error
								.getMessage()));

		}

		public void traceEntry(String location) {
		}

		public void traceEntry(String location, String message) {
		}

		private ILog getLog() {
			if (myLog == null) {
				myLog = Activator.getDefault().getLog();
			}
			return myLog;
		}

	}

}
