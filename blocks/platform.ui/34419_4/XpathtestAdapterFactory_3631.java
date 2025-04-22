package org.eclipse.e4.emf.xpath.test.model.xpathtest.impl;

import org.eclipse.e4.emf.xpath.test.model.xpathtest.ExtendedNode;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.Menu;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.MenuContainer;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.MenuElement;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.MenuItem;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.Node;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.Root;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.XpathtestFactory;
import org.eclipse.e4.emf.xpath.test.model.xpathtest.XpathtestPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

public class XpathtestPackageImpl extends EPackageImpl implements XpathtestPackage {
	private EClass rootEClass = null;

	private EClass nodeEClass = null;

	private EClass extendedNodeEClass = null;

	private EClass menuEClass = null;

	private EClass menuItemEClass = null;

	private EClass menuElementEClass = null;

	private EClass menuContainerEClass = null;

	private XpathtestPackageImpl() {
		super(eNS_URI, XpathtestFactory.eINSTANCE);
	}

	private static boolean isInited = false;

	public static XpathtestPackage init() {
		if (isInited) return (XpathtestPackage)EPackage.Registry.INSTANCE.getEPackage(XpathtestPackage.eNS_URI);

		XpathtestPackageImpl theXpathtestPackage = (XpathtestPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof XpathtestPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new XpathtestPackageImpl());

		isInited = true;

		theXpathtestPackage.createPackageContents();

		theXpathtestPackage.initializePackageContents();

		theXpathtestPackage.freeze();


		EPackage.Registry.INSTANCE.put(XpathtestPackage.eNS_URI, theXpathtestPackage);
		return theXpathtestPackage;
	}

	public EClass getRoot() {
		return rootEClass;
	}

	public EReference getRoot_Nodes() {
		return (EReference)rootEClass.getEStructuralFeatures().get(0);
	}

	public EAttribute getRoot_Id() {
		return (EAttribute)rootEClass.getEStructuralFeatures().get(1);
	}

	public EClass getNode() {
		return nodeEClass;
	}

	public EReference getNode_Parent() {
		return (EReference)nodeEClass.getEStructuralFeatures().get(0);
	}

	public EReference getNode_Children() {
		return (EReference)nodeEClass.getEStructuralFeatures().get(1);
	}

	public EReference getNode_Root() {
		return (EReference)nodeEClass.getEStructuralFeatures().get(2);
	}

	public EAttribute getNode_Cat() {
		return (EAttribute)nodeEClass.getEStructuralFeatures().get(3);
	}

	public EAttribute getNode_Value() {
		return (EAttribute)nodeEClass.getEStructuralFeatures().get(4);
	}

	public EAttribute getNode_Id() {
		return (EAttribute)nodeEClass.getEStructuralFeatures().get(5);
	}

	public EReference getNode_Inrefs() {
		return (EReference)nodeEClass.getEStructuralFeatures().get(6);
	}

	public EReference getNode_Outrefs() {
		return (EReference)nodeEClass.getEStructuralFeatures().get(7);
	}

	public EClass getExtendedNode() {
		return extendedNodeEClass;
	}

	public EAttribute getExtendedNode_Name() {
		return (EAttribute)extendedNodeEClass.getEStructuralFeatures().get(0);
	}

	public EClass getMenu() {
		return menuEClass;
	}

	public EReference getMenu_Children() {
		return (EReference)menuEClass.getEStructuralFeatures().get(0);
	}

	public EClass getMenuItem() {
		return menuItemEClass;
	}

	public EAttribute getMenuItem_Mnemonic() {
		return (EAttribute)menuItemEClass.getEStructuralFeatures().get(0);
	}

	public EClass getMenuElement() {
		return menuElementEClass;
	}

	public EAttribute getMenuElement_Id() {
		return (EAttribute)menuElementEClass.getEStructuralFeatures().get(0);
	}

	public EAttribute getMenuElement_Label() {
		return (EAttribute)menuElementEClass.getEStructuralFeatures().get(1);
	}

	public EClass getMenuContainer() {
		return menuContainerEClass;
	}

	public EReference getMenuContainer_Menus() {
		return (EReference)menuContainerEClass.getEStructuralFeatures().get(0);
	}

	public XpathtestFactory getXpathtestFactory() {
		return (XpathtestFactory)getEFactoryInstance();
	}

	private boolean isCreated = false;

	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		rootEClass = createEClass(ROOT);
		createEReference(rootEClass, ROOT__NODES);
		createEAttribute(rootEClass, ROOT__ID);

		nodeEClass = createEClass(NODE);
		createEReference(nodeEClass, NODE__PARENT);
		createEReference(nodeEClass, NODE__CHILDREN);
		createEReference(nodeEClass, NODE__ROOT);
		createEAttribute(nodeEClass, NODE__CAT);
		createEAttribute(nodeEClass, NODE__VALUE);
		createEAttribute(nodeEClass, NODE__ID);
		createEReference(nodeEClass, NODE__INREFS);
		createEReference(nodeEClass, NODE__OUTREFS);

		extendedNodeEClass = createEClass(EXTENDED_NODE);
		createEAttribute(extendedNodeEClass, EXTENDED_NODE__NAME);

		menuEClass = createEClass(MENU);
		createEReference(menuEClass, MENU__CHILDREN);

		menuItemEClass = createEClass(MENU_ITEM);
		createEAttribute(menuItemEClass, MENU_ITEM__MNEMONIC);

		menuElementEClass = createEClass(MENU_ELEMENT);
		createEAttribute(menuElementEClass, MENU_ELEMENT__ID);
		createEAttribute(menuElementEClass, MENU_ELEMENT__LABEL);

		menuContainerEClass = createEClass(MENU_CONTAINER);
		createEReference(menuContainerEClass, MENU_CONTAINER__MENUS);
	}

	private boolean isInitialized = false;

	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);



		nodeEClass.getESuperTypes().add(this.getMenuContainer());
		extendedNodeEClass.getESuperTypes().add(this.getNode());
		menuEClass.getESuperTypes().add(this.getMenuElement());
		menuItemEClass.getESuperTypes().add(this.getMenuElement());

		initEClass(rootEClass, Root.class, "Root", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRoot_Nodes(), this.getNode(), null, "nodes", null, 0, -1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoot_Id(), ecorePackage.getEString(), "id", null, 0, 1, Root.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNode_Parent(), this.getNode(), this.getNode_Children(), "parent", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNode_Children(), this.getNode(), this.getNode_Parent(), "children", null, 0, -1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNode_Root(), this.getRoot(), null, "root", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_Cat(), ecorePackage.getEString(), "cat", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_Value(), ecorePackage.getEString(), "value", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNode_Id(), ecorePackage.getEString(), "id", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNode_Inrefs(), this.getNode(), this.getNode_Outrefs(), "inrefs", null, 0, -1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNode_Outrefs(), this.getNode(), this.getNode_Inrefs(), "outrefs", null, 0, -1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(extendedNodeEClass, ExtendedNode.class, "ExtendedNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExtendedNode_Name(), ecorePackage.getEString(), "name", null, 0, 1, ExtendedNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuEClass, Menu.class, "Menu", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMenu_Children(), this.getMenuElement(), null, "children", null, 0, -1, Menu.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuItemEClass, MenuItem.class, "MenuItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMenuItem_Mnemonic(), ecorePackage.getEChar(), "mnemonic", null, 0, 1, MenuItem.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuElementEClass, MenuElement.class, "MenuElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMenuElement_Id(), ecorePackage.getEString(), "id", null, 0, 1, MenuElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMenuElement_Label(), ecorePackage.getEString(), "label", null, 0, 1, MenuElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(menuContainerEClass, MenuContainer.class, "MenuContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMenuContainer_Menus(), this.getMenuElement(), null, "menus", null, 0, -1, MenuContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		createResource(eNS_URI);
	}

} //XpathtestPackageImpl
