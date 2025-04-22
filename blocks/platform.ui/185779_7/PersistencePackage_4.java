package org.eclipse.e4.ui.workbench.internal.persistence;

import org.eclipse.emf.ecore.EFactory;

public interface PersistenceFactory extends EFactory {

	PersistenceFactory eINSTANCE = org.eclipse.e4.ui.workbench.internal.persistence.impl.PersistenceFactoryImpl.init();

	WorkbenchState createWorkbenchState();

	PartMemento createPartMemento();

	PersistencePackage getPersistencePackage();

} // PersistenceFactory
