    
    public boolean canFinish() {
    	if (this.mainPage.useNestedWizard()) {
    		return this.mainPage.getNestedWizard().canFinish();
    	} else {
    		return super.canFinish();
    	}
    }
    
