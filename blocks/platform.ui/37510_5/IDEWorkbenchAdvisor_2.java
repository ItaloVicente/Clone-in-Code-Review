		IEclipseContext context = getWorkbenchConfigurer().getWorkbench().getService(IEclipseContext.class);
		String location = (String) context.get(E4Workbench.FORCED_SHOW_LOCATION);
		if (location != null) {
			return location;
		}
		if (IDEWorkbenchPlugin.getDefault().getPreferenceStore().getBoolean(IDEInternalPreferences.SHOW_LOCATION)) {
			return Platform.getLocation().toOSString();
		}
		return null;
