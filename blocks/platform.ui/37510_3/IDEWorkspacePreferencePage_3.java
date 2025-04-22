	public static String getWorkspaceLocationForWindowTitle(IEclipseContext context) {
		String workspaceLocationFromCommandLine = getWorkspaceLocationFromCommandLine(context);
		if (workspaceLocationFromCommandLine != null) {
			return workspaceLocationFromCommandLine;
		}
		if (IDEWorkbenchPlugin.getDefault().getPreferenceStore().getBoolean(IDEInternalPreferences.SHOW_LOCATION)) {
			return getTrueWorkspaceLocation();
		}
		return null;
	}

	private static String getTrueWorkspaceLocation() {
		return Platform.getLocation().toOSString();
	}

	private static String getWorkspaceLocationFromCommandLine(IEclipseContext context) {
		String showLocation = (String) context.get(E4Workbench.SHOW_LOCATION);
		if (showLocation != null) { // arg is present
			if (showLocation.length() > 0) {
				return showLocation; // second arg value
			}
			return getTrueWorkspaceLocation(); // no second arg value
		}
		return null; // no arg at all
	}

