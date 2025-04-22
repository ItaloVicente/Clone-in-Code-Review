	private static final boolean DEBUG = WorkbenchSWTActivator.getDefault().getDebugOptions()
			.getBooleanOption(WorkbenchSWTActivator.PI_RENDERERS + Policy.MENUS, false);

	static boolean isDebugEnabled() {
		return DEBUG;
	}

