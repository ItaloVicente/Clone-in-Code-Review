	public EClass getFrame() {
		return frameEClass;
	}


	public EAttribute getFrame_X() {
		return (EAttribute)frameEClass.getEStructuralFeatures().get(0);
	}


	public EAttribute getFrame_Y() {
		return (EAttribute)frameEClass.getEStructuralFeatures().get(1);
	}


	public EAttribute getFrame_Width() {
		return (EAttribute)frameEClass.getEStructuralFeatures().get(2);
	}


	public EAttribute getFrame_Height() {
		return (EAttribute)frameEClass.getEStructuralFeatures().get(3);
	}


	public EClass getFrameElement() {
		return frameElementEClass;
	}


