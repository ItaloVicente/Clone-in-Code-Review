	private static final String TAG_ACTIVE = "active"; //$NON-NLS-1$
	private static final String ORG_ECLIPSE_E4_UI_WORKBENCH_RENDERERS_SWT = "org.eclipse.e4.ui.workbench.renderers.swt"; //$NON-NLS-1$

	public Boolean isEnableMRU() {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(ORG_ECLIPSE_E4_UI_WORKBENCH_RENDERERS_SWT);
		boolean initialMRUValue = preferences.getBoolean(StackRenderer.MRU_KEY_DEFAULT, StackRenderer.MRU_DEFAULT);
		boolean enableMRU = preferences.getBoolean(StackRenderer.MRU_KEY, initialMRUValue);
		return enableMRU;
	}

