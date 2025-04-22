	private static final String QUICK_ACCESS_DEFAULT_VISIBILITY_CHANGED = "QUICK_ACCESS_DEFAULT_VISIBILITY_CHANGED"; //$NON-NLS-1$

	private static boolean hidesQuickAccessPerDefault() {
		IPreferenceStore preferenceStore = PlatformUI.getPreferenceStore();
		return preferenceStore.getBoolean(IWorkbenchPreferenceConstants.HIDE_QUICK_ACCESS_PER_DEFAULT);
	}
