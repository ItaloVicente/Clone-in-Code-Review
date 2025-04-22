package org.eclipse.ui.internal.persistence.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.ui.internal.persistence.PartMemento;
import org.eclipse.ui.internal.persistence.PersistenceFactory;
import org.eclipse.ui.internal.persistence.PersistencePackage;
import org.eclipse.ui.internal.persistence.WorkbenchState;

public class PersistenceFactoryImpl extends EFactoryImpl implements PersistenceFactory {

	@SuppressWarnings("javadoc")
	public static PersistenceFactory init() {
		try {
			PersistenceFactory thePersistenceFactory = (PersistenceFactory)EPackage.Registry.INSTANCE.getEFactory(PersistencePackage.eNS_URI);
			if (thePersistenceFactory != null) {
				return thePersistenceFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PersistenceFactoryImpl();
	}

	public PersistenceFactoryImpl() {
		super();
	}

	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case PersistencePackage.WORKBENCH_STATE: return createWorkbenchState();
			case PersistencePackage.PART_MEMENTO: return createPartMemento();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public WorkbenchState createWorkbenchState() {
		WorkbenchStateImpl workbenchState = new WorkbenchStateImpl();
		return workbenchState;
	}

	@Override
	public PartMemento createPartMemento() {
		PartMementoImpl partMemento = new PartMementoImpl();
		return partMemento;
	}

	@Override
	public PersistencePackage getPersistencePackage() {
		return (PersistencePackage)getEPackage();
	}

	@SuppressWarnings("javadoc")
	@Deprecated
	public static PersistencePackage getPackage() {
		return PersistencePackage.eINSTANCE;
	}

} // PersistenceFactoryImpl
