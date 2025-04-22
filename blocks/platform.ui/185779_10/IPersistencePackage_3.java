package org.eclipse.e4.ui.workbench.internal.persistence;

import org.eclipse.emf.ecore.EFactory;

public interface IPersistenceFactory extends EFactory {
	IPersistenceFactory eINSTANCE = org.eclipse.e4.ui.workbench.internal.persistence.impl.PersistenceFactory.init();

	IWorkbenchState createWorkbenchState();

	IPartMemento createPartMemento();

	IPersistencePackage getPersistencePackage();

} //IPersistenceFactory
