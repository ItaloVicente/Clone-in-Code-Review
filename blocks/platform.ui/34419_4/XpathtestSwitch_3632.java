package org.eclipse.e4.emf.xpath.test.model.xpathtest.util;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

public class XpathtestAdapterFactory extends AdapterFactoryImpl {
	protected static XpathtestPackage modelPackage;

	public XpathtestAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = XpathtestPackage.eINSTANCE;
		}
	}

	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	protected XpathtestSwitch<Adapter> modelSwitch =
		new XpathtestSwitch<Adapter>() {
			@Override
			public Adapter caseRoot(Root object) {
				return createRootAdapter();
			}
			@Override
			public Adapter caseNode(Node object) {
				return createNodeAdapter();
			}
			@Override
			public Adapter caseExtendedNode(ExtendedNode object) {
				return createExtendedNodeAdapter();
			}
			@Override
			public Adapter caseMenu(Menu object) {
				return createMenuAdapter();
			}
			@Override
			public Adapter caseMenuItem(MenuItem object) {
				return createMenuItemAdapter();
			}
			@Override
			public Adapter caseMenuElement(MenuElement object) {
				return createMenuElementAdapter();
			}
			@Override
			public Adapter caseMenuContainer(MenuContainer object) {
				return createMenuContainerAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	public Adapter createRootAdapter() {
		return null;
	}

	public Adapter createNodeAdapter() {
		return null;
	}

	public Adapter createExtendedNodeAdapter() {
		return null;
	}

	public Adapter createMenuAdapter() {
		return null;
	}

	public Adapter createMenuItemAdapter() {
		return null;
	}

	public Adapter createMenuElementAdapter() {
		return null;
	}

	public Adapter createMenuContainerAdapter() {
		return null;
	}

	public Adapter createEObjectAdapter() {
		return null;
	}

} //XpathtestAdapterFactory
