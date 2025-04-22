	protected IPreferenceStore doGetSecondaryPreferenceStore() {
		return org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore();
	}

	@Override
	public void init(final IWorkbench workbench) {
