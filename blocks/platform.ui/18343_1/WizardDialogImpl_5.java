package org.eclipse.e4.ui.model.application.ui.basic.impl;

import org.eclipse.e4.ui.model.application.ui.basic.MDialog;

import org.eclipse.emf.ecore.EClass;

public class DialogImpl extends WindowImpl implements MDialog {
	protected DialogImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BasicPackageImpl.Literals.DIALOG;
	}

} //DialogImpl
