
	/**
	 * Returns the meta object for class '{@link org.eclipse.e4.ui.model.application.ui.MElementContainer <em>Element Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Container</em>'.
	 * @see org.eclipse.e4.ui.model.application.ui.MElementContainer
	 * @since 1.0
	 * @generated
	 */
	public EClass getElementContainer() {
		return elementContainerEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.e4.ui.model.application.ui.MElementContainer#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.eclipse.e4.ui.model.application.ui.MElementContainer#getChildren()
	 * @see #getElementContainer()
	 * @since 1.0
	 * @generated
	 */
	public EReference getElementContainer_Children() {
		return (EReference)elementContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.e4.ui.model.application.ui.MElementContainer#getSelectedElement <em>Selected Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Selected Element</em>'.
	 * @see org.eclipse.e4.ui.model.application.ui.MElementContainer#getSelectedElement()
	 * @see #getElementContainer()
	 * @since 1.0
	 * @generated
	 */
	public EReference getElementContainer_SelectedElement() {
		return (EReference)elementContainerEClass.getEStructuralFeatures().get(1);
	}

