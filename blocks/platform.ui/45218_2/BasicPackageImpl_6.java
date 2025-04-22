	public EClass getWizard() {
		return wizardEClass;
	}


	public EClass getDialogElement() {
		return dialogElementEClass;
	}


	public EClass getWizardElement() {
		return wizardElementEClass;
	}


	public EClass getFrame() {
		return frameEClass;
	}


	public EAttribute getFrame_X() {
		return (EAttribute)frameEClass.getEStructuralFeatures().get(0);
	}


	public EAttribute getFrame_Y() {
		return (EAttribute)frameEClass.getEStructuralFeatures().get(1);
	}


