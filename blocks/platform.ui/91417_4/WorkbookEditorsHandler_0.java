	public Boolean isEnableMRU() {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(ORG_ECLIPSE_E4_UI_WORKBENCH_RENDERERS_SWT);
		boolean initialMRUValue = preferences.getBoolean(StackRenderer.MRU_KEY_DEFAULT, StackRenderer.MRU_DEFAULT);
		boolean enableMRU = preferences.getBoolean(StackRenderer.MRU_KEY, initialMRUValue);
		return enableMRU;
	}

