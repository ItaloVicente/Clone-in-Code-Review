	private IEclipsePreferences getSwtRendererPreferences() {
		return InstanceScope.INSTANCE.getNode("org.eclipse.e4.ui.workbench.renderers.swt"); //$NON-NLS-1$
	}
	
	private boolean getDefaultMRUValue() {
		return getSwtRendererPreferences().getBoolean(StackRenderer.MRU_KEY_DEFAULT, true);
	}
	
