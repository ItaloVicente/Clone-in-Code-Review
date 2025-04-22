package org.eclipse.e4.ui.workbench.internal.persistence.impl;

import org.eclipse.e4.ui.workbench.internal.persistence.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

public class PersistenceFactory extends EFactoryImpl implements IPersistenceFactory {
	public static IPersistenceFactory init() {
		try {
			IPersistenceFactory thePersistenceFactory = (IPersistenceFactory)EPackage.Registry.INSTANCE.getEFactory(IPersistencePackage.eNS_URI);
			if (thePersistenceFactory != null) {
				return thePersistenceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PersistenceFactory();
	}

	public PersistenceFactory() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case IPersistencePackage.WORKBENCH_STATE: return createWorkbenchState();
			case IPersistencePackage.PART_MEMENTO: return createPartMemento();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public IWorkbenchState createWorkbenchState() {
		WorkbenchState workbenchState = new WorkbenchState();
		return workbenchState;
	}

	@Override
	public IPartMemento createPartMemento() {
		PartMemento partMemento = new PartMemento();
		return partMemento;
	}

	@Override
	public IPersistencePackage getPersistencePackage() {
		return (IPersistencePackage)getEPackage();
	}

	@Deprecated
	public static IPersistencePackage getPackage() {
		return IPersistencePackage.eINSTANCE;
	}

} //PersistenceFactory
