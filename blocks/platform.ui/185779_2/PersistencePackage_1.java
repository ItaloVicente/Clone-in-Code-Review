package org.eclipse.ui.internal.persistence;

import org.eclipse.emf.ecore.EFactory;

public interface PersistenceFactory extends EFactory {

	PersistenceFactory eINSTANCE = org.eclipse.ui.internal.persistence.impl.PersistenceFactoryImpl.init();

	WorkbenchState createWorkbenchState();

	PartMemento createPartMemento();

	PersistencePackage getPersistencePackage();

} // PersistenceFactory
