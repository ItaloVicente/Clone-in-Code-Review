	public IWizard getNestedWizard() {
		if (!useNestedWizard()) {
			return null;
		}
		Button selectedButton = null;
		if (this.referenceAsNewProjectRadio.getSelection()) { selectedButton = this.referenceAsNewProjectRadio; }
		if (this.copyAsNewProjectRadio.getSelection()) { selectedButton = this.copyAsNewProjectRadio; }
		if (this.referenceExistingProjectRadio.getSelection()) { selectedButton = this.referenceExistingProjectRadio; }
		if (this.resourcesRadio.getSelection()) { selectedButton = this.resourcesRadio; }
		
		if (this.nestedWizard == null ||
			!this.source.equals(this.lastSource) || this.lastSelectedAction != selectedButton) {
			if (this.nestedWizard != null) {
				this.nestedWizard.dispose();
			}
			this.lastSource = this.source;
			this.lastSelectedAction = selectedButton;
			if (lastSelectedAction.equals(this.referenceAsNewProjectRadio) || lastSelectedAction.equals(this.copyAsNewProjectRadio)) {
				BasicNewProjectResourceWizard wizard = new BasicNewProjectResourceWizard();
				wizard.init(PlatformUI.getWorkbench(), null);
				if (lastSelectedAction.equals(this.referenceAsNewProjectRadio)) {
					wizard.setDefaultLocation(this.lastSource, true);
				} else {
					wizard.setDefaultProjectName(this.lastSource.getName());
				}
				this.nestedWizard = wizard;
			} else {
				this.nestedWizard = new ExternalProjectImportWizard(this.lastSource.getPath());
			}
		}
		this.nestedWizard.addPages();
		return this.nestedWizard;
	}
    
    public IWizardPage getNextPage() {
    	if (useNestedWizard()) {
    		return getNestedWizard().getPages()[0];
    	}
    	return super.getNextPage();
    }
