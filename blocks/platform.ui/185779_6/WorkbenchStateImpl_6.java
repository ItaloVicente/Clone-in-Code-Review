package org.eclipse.e4.ui.workbench.internal.persistence.impl;

import org.eclipse.e4.ui.model.application.impl.ApplicationPackageImpl;
import org.eclipse.e4.ui.model.application.ui.advanced.impl.AdvancedPackageImpl;
import org.eclipse.e4.ui.model.application.ui.basic.impl.BasicPackageImpl;
import org.eclipse.e4.ui.workbench.internal.persistence.PartMemento;
import org.eclipse.e4.ui.workbench.internal.persistence.PersistenceFactory;
import org.eclipse.e4.ui.workbench.internal.persistence.PersistencePackage;
import org.eclipse.e4.ui.workbench.internal.persistence.WorkbenchState;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

public class PersistencePackageImpl extends EPackageImpl implements PersistencePackage {

	private EClass workbenchStateEClass = null;

	private EClass partMementoEClass = null;

	private PersistencePackageImpl() {
		super(eNS_URI, PersistenceFactory.eINSTANCE);
	}

	private static boolean isInited = false;

	public static PersistencePackage init() {
		if (isInited) return (PersistencePackage)EPackage.Registry.INSTANCE.getEPackage(PersistencePackage.eNS_URI);

		Object registeredPersistencePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		PersistencePackageImpl thePersistencePackage = registeredPersistencePackage instanceof PersistencePackageImpl ? (PersistencePackageImpl)registeredPersistencePackage : new PersistencePackageImpl();

		isInited = true;

		ApplicationPackageImpl.eINSTANCE.eClass();

		thePersistencePackage.createPackageContents();

		thePersistencePackage.initializePackageContents();

		thePersistencePackage.freeze();

		EPackage.Registry.INSTANCE.put(PersistencePackage.eNS_URI, thePersistencePackage);
		return thePersistencePackage;
	}

	@Override
	public EClass getWorkbenchState() {
		return workbenchStateEClass;
	}

	@Override
	public EReference getWorkbenchState_Perspective() {
		return (EReference)workbenchStateEClass.getEStructuralFeatures().get(0);
	}

	@Override
	public EReference getWorkbenchState_ViewSettings() {
		return (EReference)workbenchStateEClass.getEStructuralFeatures().get(1);
	}

	@Override
	public EReference getWorkbenchState_EditorArea() {
		return (EReference)workbenchStateEClass.getEStructuralFeatures().get(2);
	}

	@Override
	public EReference getWorkbenchState_TrimBars() {
		return (EReference)workbenchStateEClass.getEStructuralFeatures().get(3);
	}

	@Override
	public EClass getPartMemento() {
		return partMementoEClass;
	}

	@Override
	public EAttribute getPartMemento_PartId() {
		return (EAttribute)partMementoEClass.getEStructuralFeatures().get(0);
	}

	@Override
	public EAttribute getPartMemento_Memento() {
		return (EAttribute)partMementoEClass.getEStructuralFeatures().get(1);
	}

	@Override
	public PersistenceFactory getPersistenceFactory() {
		return (PersistenceFactory)getEFactoryInstance();
	}

	private boolean isCreated = false;

	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		workbenchStateEClass = createEClass(WORKBENCH_STATE);
		createEReference(workbenchStateEClass, WORKBENCH_STATE__PERSPECTIVE);
		createEReference(workbenchStateEClass, WORKBENCH_STATE__VIEW_SETTINGS);
		createEReference(workbenchStateEClass, WORKBENCH_STATE__EDITOR_AREA);
		createEReference(workbenchStateEClass, WORKBENCH_STATE__TRIM_BARS);

		partMementoEClass = createEClass(PART_MEMENTO);
		createEAttribute(partMementoEClass, PART_MEMENTO__PART_ID);
		createEAttribute(partMementoEClass, PART_MEMENTO__MEMENTO);
	}

	private boolean isInitialized = false;

	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		AdvancedPackageImpl theAdvancedPackage = (AdvancedPackageImpl)EPackage.Registry.INSTANCE.getEPackage(AdvancedPackageImpl.eNS_URI);
		BasicPackageImpl theBasicPackage = (BasicPackageImpl)EPackage.Registry.INSTANCE.getEPackage(BasicPackageImpl.eNS_URI);




		initEClass(workbenchStateEClass, WorkbenchState.class, "WorkbenchState", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getWorkbenchState_Perspective(), theAdvancedPackage.getPerspective(), null, "perspective", null, 0, 1, WorkbenchState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getWorkbenchState_ViewSettings(), this.getPartMemento(), null, "viewSettings", null, 0, -1, WorkbenchState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getWorkbenchState_EditorArea(), theAdvancedPackage.getArea(), null, "editorArea", null, 0, 1, WorkbenchState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getWorkbenchState_TrimBars(), theBasicPackage.getTrimBar(), null, "trimBars", null, 0, -1, WorkbenchState.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(partMementoEClass, PartMemento.class, "PartMemento", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPartMemento_PartId(), ecorePackage.getEString(), "partId", null, 0, 1, PartMemento.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getPartMemento_Memento(), ecorePackage.getEString(), "memento", null, 0, 1, PartMemento.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		createResource(eNS_URI);
	}

} // PersistencePackageImpl
