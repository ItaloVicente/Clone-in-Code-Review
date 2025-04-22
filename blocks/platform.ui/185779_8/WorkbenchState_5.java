package org.eclipse.e4.ui.workbench.internal.persistence;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

@SuppressWarnings("hiding")
public interface PersistencePackage extends EPackage {

	String eNAME = "persistence"; //$NON-NLS-1$

	String eNS_URI = "http://www.eclipse.org/ui/persistence"; //$NON-NLS-1$

	String eNS_PREFIX = "org.eclipse.ui.persistence"; //$NON-NLS-1$

	PersistencePackage eINSTANCE = org.eclipse.e4.ui.workbench.internal.persistence.impl.PersistencePackageImpl.init();

	int WORKBENCH_STATE = 0;

	int WORKBENCH_STATE__PERSPECTIVE = 0;

	int WORKBENCH_STATE__VIEW_SETTINGS = 1;

	int WORKBENCH_STATE__EDITOR_AREA = 2;

	int WORKBENCH_STATE__TRIM_BARS = 3;

	int WORKBENCH_STATE_FEATURE_COUNT = 4;

	int WORKBENCH_STATE_OPERATION_COUNT = 0;

	int PART_MEMENTO = 1;

	int PART_MEMENTO__PART_ID = 0;

	int PART_MEMENTO__MEMENTO = 1;

	int PART_MEMENTO_FEATURE_COUNT = 2;

	int PART_MEMENTO_OPERATION_COUNT = 0;

	EClass getWorkbenchState();

	EReference getWorkbenchState_Perspective();

	EReference getWorkbenchState_ViewSettings();

	EReference getWorkbenchState_EditorArea();

	EReference getWorkbenchState_TrimBars();

	EClass getPartMemento();

	EAttribute getPartMemento_PartId();

	EAttribute getPartMemento_Memento();

	PersistenceFactory getPersistenceFactory();

	interface Literals {

		EClass WORKBENCH_STATE = eINSTANCE.getWorkbenchState();

		EReference WORKBENCH_STATE__PERSPECTIVE = eINSTANCE.getWorkbenchState_Perspective();

		EReference WORKBENCH_STATE__VIEW_SETTINGS = eINSTANCE.getWorkbenchState_ViewSettings();

		EReference WORKBENCH_STATE__EDITOR_AREA = eINSTANCE.getWorkbenchState_EditorArea();

		EReference WORKBENCH_STATE__TRIM_BARS = eINSTANCE.getWorkbenchState_TrimBars();

		EClass PART_MEMENTO = eINSTANCE.getPartMemento();

		EAttribute PART_MEMENTO__PART_ID = eINSTANCE.getPartMemento_PartId();

		EAttribute PART_MEMENTO__MEMENTO = eINSTANCE.getPartMemento_Memento();

	}

} // PersistencePackage
