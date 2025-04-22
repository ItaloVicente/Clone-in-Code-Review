package org.eclipse.e4.emf.xpath.test.model.xpathtest;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

public interface MenuContainer extends EObject {
	EList<MenuElement> getMenus();

} // MenuContainer
