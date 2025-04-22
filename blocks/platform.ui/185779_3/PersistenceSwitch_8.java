package org.eclipse.ui.internal.persistence.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.internal.persistence.PartMemento;
import org.eclipse.ui.internal.persistence.PersistencePackage;
import org.eclipse.ui.internal.persistence.WorkbenchState;

public class PersistenceAdapterFactory extends AdapterFactoryImpl {

	protected static PersistencePackage modelPackage;

	public PersistenceAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PersistencePackage.eINSTANCE;
		}
	}

	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	protected PersistenceSwitch<Adapter> modelSwitch = new PersistenceSwitch<Adapter>() {
			@Override
			public Adapter caseWorkbenchState(WorkbenchState object) {
				return createWorkbenchStateAdapter();
			}
			@Override
			public Adapter casePartMemento(PartMemento object) {
				return createPartMementoAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	public Adapter createWorkbenchStateAdapter() {
		return null;
	}

	public Adapter createPartMementoAdapter() {
		return null;
	}

	public Adapter createEObjectAdapter() {
		return null;
	}

} // PersistenceAdapterFactory
