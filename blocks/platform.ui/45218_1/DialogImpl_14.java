package org.eclipse.e4.ui.model.application.ui.basic.impl;

import org.eclipse.e4.ui.model.application.ui.basic.MDialogElement;

import org.eclipse.e4.ui.model.application.ui.impl.UIElementImpl;

import org.eclipse.emf.ecore.EClass;

public class DialogElementImpl extends UIElementImpl implements MDialogElement {
	protected DialogElementImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BasicPackageImpl.Literals.DIALOG_ELEMENT;
	}

} //DialogElementImpl
