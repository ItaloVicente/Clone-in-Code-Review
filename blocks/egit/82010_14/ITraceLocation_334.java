package org.eclipse.egit.ui.internal.trace;

import org.eclipse.egit.ui.Activator;
import org.eclipse.osgi.service.debug.DebugOptions;
import org.eclipse.osgi.service.debug.DebugTrace;

public enum GitTraceLocation implements ITraceLocation {

	UI("/debug/ui"), //$NON-NLS-1$
	HISTORYVIEW("/debug/ui/historyview"), //$NON-NLS-1$
	REPOSITORIESVIEW("/debug/ui/repositoriesview"), //$NON-NLS-1$
	REPOSITORYCHANGESCANNER("/debug/repochangescanner"), //$NON-NLS-1$
	DECORATION("/debug/ui/decoration"), //$NON-NLS-1$
	QUICKDIFF("/debug/quickdiff"), //$NON-NLS-1$
	PROPERTIESTESTER("/debug/ui/propertiestesters"), //$NON-NLS-1$
	SELECTION("/debug/ui/selection"); //$NON-NLS-1$

	public static void initializeFromOptions(DebugOptions options,
			boolean pluginIsDebugging) {

		if (pluginIsDebugging) {
			myTrace = options.newDebugTrace(Activator.getPluginId());

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

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public String getLocation() {
		return this.location;
	}

	private String getFullPath() {
		return this.fullPath;
	}

	private void setActive(boolean active) {
		this.active = active;
	}
}
