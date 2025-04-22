	public Wizard getNestedWizard() {
		if (!useNestedWizard()) {
			return null;
		}
		if (this.nestedWizard == null) {
			this.nestedWizard = new BasicNewProjectResourceWizard();
			this.nestedWizard.init(PlatformUI.getWorkbench(), null);
			this.nestedWizard.setDefaultLocation(this.source);
			this.nestedWizard.addPages();
		}
		return this.nestedWizard;
	}
    
    public IWizardPage getNextPage() {
    	if (useNestedWizard()) {
    		return getNestedWizard().getPages()[0];
    	}
    	return super.getNextPage();
    }
