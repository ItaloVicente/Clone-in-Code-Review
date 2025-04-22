package org.eclipse.e4.ui.model.application.ui.basic.impl;

import java.util.List;

import org.eclipse.e4.ui.model.application.ui.MUIElement;

import org.eclipse.e4.ui.model.application.ui.basic.MWindowElement;
import org.eclipse.e4.ui.model.application.ui.basic.MWizard;

import org.eclipse.e4.ui.model.application.ui.impl.ElementContainerImpl;
import org.eclipse.e4.ui.model.application.ui.impl.UiPackageImpl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;

public class WizardImpl extends ElementContainerImpl<MWindowElement> implements MWizard {
	protected WizardImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BasicPackageImpl.Literals.WIZARD;
	}

	@Override
	public List<MWindowElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<MWindowElement>(MWindowElement.class, this, BasicPackageImpl.WIZARD__CHILDREN, UiPackageImpl.UI_ELEMENT__PARENT) { private static final long serialVersionUID = 1L; @Override public Class<?> getInverseFeatureClass() { return MUIElement.class; } };
		}
		return children;
	}

	@Override
	public void setSelectedElement(MWindowElement newSelectedElement) {
		super.setSelectedElement(newSelectedElement);
	}

} //WizardImpl
