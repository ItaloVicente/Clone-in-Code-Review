	public List<MWizardDialog> getWizards() {
		if (wizards == null) {
			wizards = new EObjectContainmentEList<MWizardDialog>(MWizardDialog.class, this, ApplicationPackageImpl.APPLICATION__WIZARDS);
		}
		return wizards;
	}

