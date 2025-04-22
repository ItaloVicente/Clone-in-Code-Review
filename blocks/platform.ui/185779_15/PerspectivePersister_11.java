package org.eclipse.e4.ui.workbench.internal.persistence.util;

import org.eclipse.e4.ui.workbench.internal.persistence.*;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

public class PersistenceSwitch<T> extends Switch<T> {

	protected static IPersistencePackage modelPackage;

	public PersistenceSwitch() {
		if (modelPackage == null) {
			modelPackage = IPersistencePackage.eINSTANCE;
		}
	}

	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case IPersistencePackage.WORKBENCH_STATE: {
				IWorkbenchState workbenchState = (IWorkbenchState)theEObject;
				T result = caseWorkbenchState(workbenchState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IPersistencePackage.PART_MEMENTO: {
				IPartMemento partMemento = (IPartMemento)theEObject;
				T result = casePartMemento(partMemento);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	public T caseWorkbenchState(IWorkbenchState object) {
		return null;
	}

	public T casePartMemento(IPartMemento object) {
		return null;
	}

	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} // PersistenceSwitch
