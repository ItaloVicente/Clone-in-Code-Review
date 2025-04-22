package org.eclipse.ui.internal.persistence.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.ui.internal.persistence.PartMemento;
import org.eclipse.ui.internal.persistence.PersistencePackage;
import org.eclipse.ui.internal.persistence.WorkbenchState;

public class PersistenceSwitch<T> extends Switch<T> {

	protected static PersistencePackage modelPackage;

	public PersistenceSwitch() {
		if (modelPackage == null) {
			modelPackage = PersistencePackage.eINSTANCE;
		}
	}

	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case PersistencePackage.WORKBENCH_STATE: {
				WorkbenchState workbenchState = (WorkbenchState)theEObject;
				T result = caseWorkbenchState(workbenchState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case PersistencePackage.PART_MEMENTO: {
				PartMemento partMemento = (PartMemento)theEObject;
				T result = casePartMemento(partMemento);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	public T caseWorkbenchState(WorkbenchState object) {
		return null;
	}

	public T casePartMemento(PartMemento object) {
		return null;
	}

	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // PersistenceSwitch
