package org.eclipse.e4.emf.xpath.test.model.xpathtest.impl;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

public class XpathtestFactoryImpl extends EFactoryImpl implements XpathtestFactory {
	public static XpathtestFactory init() {
		try {
			XpathtestFactory theXpathtestFactory = (XpathtestFactory)EPackage.Registry.INSTANCE.getEFactory(XpathtestPackage.eNS_URI);
			if (theXpathtestFactory != null) {
				return theXpathtestFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new XpathtestFactoryImpl();
	}

	public XpathtestFactoryImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case XpathtestPackage.ROOT: return createRoot();
			case XpathtestPackage.NODE: return createNode();
			case XpathtestPackage.EXTENDED_NODE: return createExtendedNode();
			case XpathtestPackage.MENU: return createMenu();
			case XpathtestPackage.MENU_ITEM: return createMenuItem();
			case XpathtestPackage.MENU_ELEMENT: return createMenuElement();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	public Root createRoot() {
		RootImpl root = new RootImpl();
		return root;
	}

	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}

	public ExtendedNode createExtendedNode() {
		ExtendedNodeImpl extendedNode = new ExtendedNodeImpl();
		return extendedNode;
	}

	public Menu createMenu() {
		MenuImpl menu = new MenuImpl();
		return menu;
	}

	public MenuItem createMenuItem() {
		MenuItemImpl menuItem = new MenuItemImpl();
		return menuItem;
	}

	public MenuElement createMenuElement() {
		MenuElementImpl menuElement = new MenuElementImpl();
		return menuElement;
	}

	public XpathtestPackage getXpathtestPackage() {
		return (XpathtestPackage)getEPackage();
	}

	@Deprecated
	public static XpathtestPackage getPackage() {
		return XpathtestPackage.eINSTANCE;
	}

} //XpathtestFactoryImpl
