	 * The <code>WizardDialog</code> implementation of this
	 * <code>Window</code> method calls call <code>IWizard.addPages</code>
	 * to allow the current wizard to add extra pages, then
	 * <code>super.createContents</code> to create the controls. It then calls
	 * <code>IWizard.createPageControls</code> to allow the wizard to
	 * pre-create their page controls prior to opening, so that the wizard opens
	 * to the correct size. And finally it shows the first page.
