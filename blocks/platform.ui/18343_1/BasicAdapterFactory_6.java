package org.eclipse.e4.ui.model.application.ui.basic.impl;

import org.eclipse.e4.ui.model.application.ui.basic.MWizardDialog;

import org.eclipse.emf.ecore.EClass;

public class WizardDialogImpl extends DialogImpl implements MWizardDialog {
	protected WizardDialogImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BasicPackageImpl.Literals.WIZARD_DIALOG;
	}

} //WizardDialogImpl
