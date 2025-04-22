package org.eclipse.e4.emf.xpath.test.model.xpathtest;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

public interface XpathtestPackage extends EPackage {
	String eNAME = "xpathtest";

	String eNS_URI = "http://www.eclipse.org/emf/xpathtest";

	String eNS_PREFIX = "xpathtest";

	XpathtestPackage eINSTANCE = org.eclipse.e4.emf.xpath.test.model.xpathtest.impl.XpathtestPackageImpl.init();

	int ROOT = 0;

	int ROOT__NODES = 0;

	int ROOT__ID = 1;

	int ROOT_FEATURE_COUNT = 2;

	int MENU_CONTAINER = 6;

	int MENU_CONTAINER__MENUS = 0;

	int MENU_CONTAINER_FEATURE_COUNT = 1;

	int NODE = 1;

	int NODE__MENUS = MENU_CONTAINER__MENUS;

	int NODE__PARENT = MENU_CONTAINER_FEATURE_COUNT + 0;

	int NODE__CHILDREN = MENU_CONTAINER_FEATURE_COUNT + 1;

	int NODE__ROOT = MENU_CONTAINER_FEATURE_COUNT + 2;

	int NODE__CAT = MENU_CONTAINER_FEATURE_COUNT + 3;

	int NODE__VALUE = MENU_CONTAINER_FEATURE_COUNT + 4;

	int NODE__ID = MENU_CONTAINER_FEATURE_COUNT + 5;

	int NODE__INREFS = MENU_CONTAINER_FEATURE_COUNT + 6;

	int NODE__OUTREFS = MENU_CONTAINER_FEATURE_COUNT + 7;

	int NODE_FEATURE_COUNT = MENU_CONTAINER_FEATURE_COUNT + 8;


	int EXTENDED_NODE = 2;

	int EXTENDED_NODE__MENUS = NODE__MENUS;

	int EXTENDED_NODE__PARENT = NODE__PARENT;

	int EXTENDED_NODE__CHILDREN = NODE__CHILDREN;

	int EXTENDED_NODE__ROOT = NODE__ROOT;

	int EXTENDED_NODE__CAT = NODE__CAT;

	int EXTENDED_NODE__VALUE = NODE__VALUE;

	int EXTENDED_NODE__ID = NODE__ID;

	int EXTENDED_NODE__INREFS = NODE__INREFS;

	int EXTENDED_NODE__OUTREFS = NODE__OUTREFS;

	int EXTENDED_NODE__NAME = NODE_FEATURE_COUNT + 0;

	int EXTENDED_NODE_FEATURE_COUNT = NODE_FEATURE_COUNT + 1;


	int MENU_ELEMENT = 5;

	int MENU_ELEMENT__ID = 0;

	int MENU_ELEMENT__LABEL = 1;

	int MENU_ELEMENT_FEATURE_COUNT = 2;

	int MENU = 3;

	int MENU__ID = MENU_ELEMENT__ID;

	int MENU__LABEL = MENU_ELEMENT__LABEL;

	int MENU__CHILDREN = MENU_ELEMENT_FEATURE_COUNT + 0;

	int MENU_FEATURE_COUNT = MENU_ELEMENT_FEATURE_COUNT + 1;

	int MENU_ITEM = 4;

	int MENU_ITEM__ID = MENU_ELEMENT__ID;

	int MENU_ITEM__LABEL = MENU_ELEMENT__LABEL;

	int MENU_ITEM__MNEMONIC = MENU_ELEMENT_FEATURE_COUNT + 0;

	int MENU_ITEM_FEATURE_COUNT = MENU_ELEMENT_FEATURE_COUNT + 1;


	EClass getRoot();

	EReference getRoot_Nodes();

	EAttribute getRoot_Id();

	EClass getNode();

	EReference getNode_Parent();

	EReference getNode_Children();

	EReference getNode_Root();

	EAttribute getNode_Cat();

	EAttribute getNode_Value();

	EAttribute getNode_Id();

	EReference getNode_Inrefs();

	EReference getNode_Outrefs();

	EClass getExtendedNode();

	EAttribute getExtendedNode_Name();

	EClass getMenu();

	EReference getMenu_Children();

	EClass getMenuItem();

	EAttribute getMenuItem_Mnemonic();

	EClass getMenuElement();

	EAttribute getMenuElement_Id();

	EAttribute getMenuElement_Label();

	EClass getMenuContainer();

	EReference getMenuContainer_Menus();

	XpathtestFactory getXpathtestFactory();

	interface Literals {
		EClass ROOT = eINSTANCE.getRoot();

		EReference ROOT__NODES = eINSTANCE.getRoot_Nodes();

		EAttribute ROOT__ID = eINSTANCE.getRoot_Id();

		EClass NODE = eINSTANCE.getNode();

		EReference NODE__PARENT = eINSTANCE.getNode_Parent();

		EReference NODE__CHILDREN = eINSTANCE.getNode_Children();

		EReference NODE__ROOT = eINSTANCE.getNode_Root();

		EAttribute NODE__CAT = eINSTANCE.getNode_Cat();

		EAttribute NODE__VALUE = eINSTANCE.getNode_Value();

		EAttribute NODE__ID = eINSTANCE.getNode_Id();

		EReference NODE__INREFS = eINSTANCE.getNode_Inrefs();

		EReference NODE__OUTREFS = eINSTANCE.getNode_Outrefs();

		EClass EXTENDED_NODE = eINSTANCE.getExtendedNode();

		EAttribute EXTENDED_NODE__NAME = eINSTANCE.getExtendedNode_Name();

		EClass MENU = eINSTANCE.getMenu();

		EReference MENU__CHILDREN = eINSTANCE.getMenu_Children();

		EClass MENU_ITEM = eINSTANCE.getMenuItem();

		EAttribute MENU_ITEM__MNEMONIC = eINSTANCE.getMenuItem_Mnemonic();

		EClass MENU_ELEMENT = eINSTANCE.getMenuElement();

		EAttribute MENU_ELEMENT__ID = eINSTANCE.getMenuElement_Id();

		EAttribute MENU_ELEMENT__LABEL = eINSTANCE.getMenuElement_Label();

		EClass MENU_CONTAINER = eINSTANCE.getMenuContainer();

		EReference MENU_CONTAINER__MENUS = eINSTANCE.getMenuContainer_Menus();

	}

} //XpathtestPackage
