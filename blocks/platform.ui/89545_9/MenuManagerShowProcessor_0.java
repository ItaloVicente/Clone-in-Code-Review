	private static final boolean DEBUG = WorkbenchSWTActivator.getDefault().getDebugOptions()
			.getBooleanOption(WorkbenchSWTActivator.PI_RENDERERS + Policy.MENUS, false);

	static boolean isDebugEnabled() {
		return DEBUG;
	}

	private static void trace(String msg, MenuManager menuManager, MMenu menuModel) {
		if (isDebugEnabled()) {
			WorkbenchSWTActivator.trace(Policy.MENUS, msg + ": " + menuManager + ": " + menuManager.getMenu() + ": " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ menuModel, null);
		}
