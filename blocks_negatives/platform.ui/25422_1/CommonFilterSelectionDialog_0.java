	private void customize() {
		ColorRegistry reg = JFaceResources.getColorRegistry();
		Color c1 = reg.get("org.eclipse.ui.workbench.ACTIVE_TAB_BG_START"), //$NON-NLS-1$
		customizationsTabFolder.setSelectionBackground(new Color[] {c1, c2},	new int[] {100}, true);
		customizationsTabFolder.setSimple(true);
	}

	private CTabItem createTabItem(CTabFolder aTabFolder, String label,
