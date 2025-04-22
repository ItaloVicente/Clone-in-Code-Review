	public EOperation getUIElement__UpdateLocalization() {
		return uiElementEClass.getEOperations().get(0);
	}


	public EClass getElementContainer() {
		return elementContainerEClass;
	}

	public EReference getElementContainer_Children() {
		return (EReference)elementContainerEClass.getEStructuralFeatures().get(0);
	}

	public EReference getElementContainer_SelectedElement() {
		return (EReference)elementContainerEClass.getEStructuralFeatures().get(1);
	}
