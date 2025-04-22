package org.eclipse.e4.emf.xpath.test.model.xpathtest.util;

import java.util.List;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

public class XpathtestSwitch<T> extends Switch<T> {
	protected static XpathtestPackage modelPackage;

	public XpathtestSwitch() {
		if (modelPackage == null) {
			modelPackage = XpathtestPackage.eINSTANCE;
		}
	}

	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case XpathtestPackage.ROOT: {
				Root root = (Root)theEObject;
				T result = caseRoot(root);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XpathtestPackage.NODE: {
				Node node = (Node)theEObject;
				T result = caseNode(node);
				if (result == null) result = caseMenuContainer(node);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XpathtestPackage.EXTENDED_NODE: {
				ExtendedNode extendedNode = (ExtendedNode)theEObject;
				T result = caseExtendedNode(extendedNode);
				if (result == null) result = caseNode(extendedNode);
				if (result == null) result = caseMenuContainer(extendedNode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XpathtestPackage.MENU: {
				Menu menu = (Menu)theEObject;
				T result = caseMenu(menu);
				if (result == null) result = caseMenuElement(menu);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XpathtestPackage.MENU_ITEM: {
				MenuItem menuItem = (MenuItem)theEObject;
				T result = caseMenuItem(menuItem);
				if (result == null) result = caseMenuElement(menuItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XpathtestPackage.MENU_ELEMENT: {
				MenuElement menuElement = (MenuElement)theEObject;
				T result = caseMenuElement(menuElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case XpathtestPackage.MENU_CONTAINER: {
				MenuContainer menuContainer = (MenuContainer)theEObject;
				T result = caseMenuContainer(menuContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	public T caseRoot(Root object) {
		return null;
	}

	public T caseNode(Node object) {
		return null;
	}

	public T caseExtendedNode(ExtendedNode object) {
		return null;
	}

	public T caseMenu(Menu object) {
		return null;
	}

	public T caseMenuItem(MenuItem object) {
		return null;
	}

	public T caseMenuElement(MenuElement object) {
		return null;
	}

	public T caseMenuContainer(MenuContainer object) {
		return null;
	}

	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //XpathtestSwitch
