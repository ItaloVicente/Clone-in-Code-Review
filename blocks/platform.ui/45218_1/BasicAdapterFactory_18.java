package org.eclipse.e4.ui.model.application.ui.basic.impl;

import org.eclipse.e4.ui.model.application.ui.basic.MWizardElement;

import org.eclipse.e4.ui.model.application.ui.impl.UIElementImpl;

import org.eclipse.emf.ecore.EClass;

public class WizardElementImpl extends UIElementImpl implements MWizardElement {
	protected WizardElementImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BasicPackageImpl.Literals.WIZARD_ELEMENT;
	}

} //WizardElementImpl
