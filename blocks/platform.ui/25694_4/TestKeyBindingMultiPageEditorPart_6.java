	@Override
	public void setFocus() {
		final boolean odds = (number % 2) > 0;
		final String scope1 = "org.eclipse.ui.tests.scope1"; //$NON-NLS-1$
		final String scope2 = "org.eclipse.ui.tests.scope2"; //$NON-NLS-1$
		IKeyBindingService keyBindingService = getEditorSite()
				.getKeyBindingService();
		keyBindingService.setScopes(new String[] { (odds) ? scope1 : scope2 });
	}
