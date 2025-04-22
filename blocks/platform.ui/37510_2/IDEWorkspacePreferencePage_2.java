	public static String getWorkspaceLocationForWindowTitle() {
		String workspaceLocationFromCommandLine = getWorkspaceLocationFromCommandLine();
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

	private static String getWorkspaceLocationFromCommandLine() {
		String[] cmdLineArgs = Platform.getCommandLineArgs();
		for (int i = 0; i < cmdLineArgs.length; i++) {
			if ("-showlocation".equalsIgnoreCase(cmdLineArgs[i])) { //$NON-NLS-1$
				String name = null;
				if (cmdLineArgs.length > i + 1) {
					name = cmdLineArgs[i + 1];
				}
				if (name != null && name.indexOf("-") == -1) { //$NON-NLS-1$
					return name;
				}
				return getTrueWorkspaceLocation();
			}
		}
		return null;
	}

