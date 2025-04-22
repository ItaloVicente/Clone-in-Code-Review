	private static String getWorkspaceLocationFromCommandLine(IEclipseContext context) {
		String showLocation = (String) context.get(E4Workbench.FORCED_SHOW_LOCATION);
		if (showLocation != null) { // arg is present
			if (showLocation.length() > 0) {
				return showLocation; // second arg value
			}
			return Platform.getLocation().toOSString(); // no second arg value
		}
		return null; // no arg at all
	}

