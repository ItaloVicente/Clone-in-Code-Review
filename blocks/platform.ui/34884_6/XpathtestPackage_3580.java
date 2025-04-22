package org.eclipse.e4.emf.xpath.test.model.xpathtest;

import org.eclipse.emf.ecore.EFactory;

public interface XpathtestFactory extends EFactory {
	XpathtestFactory eINSTANCE = org.eclipse.e4.emf.xpath.test.model.xpathtest.impl.XpathtestFactoryImpl.init();

	Root createRoot();

	Node createNode();

	ExtendedNode createExtendedNode();

	Menu createMenu();

	MenuItem createMenuItem();

	MenuElement createMenuElement();

	XpathtestPackage getXpathtestPackage();

} //XpathtestFactory
