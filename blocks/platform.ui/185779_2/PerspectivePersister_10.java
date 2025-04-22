package org.eclipse.ui.persistence;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.internal.persistence.WorkbenchState;
import org.eclipse.ui.persistence.impl.PerspectiveMigratorImpl;

public interface PerspectiveMigrator {

	public static PerspectiveMigrator INSTANCE = new PerspectiveMigratorImpl();

	void apply3xWorkbenchState(String iMemento) throws WorkbenchException;

	WorkbenchState convertToWorkbenchState(MApplication application);

	MApplication convertToMApplication(String iMemento) throws WorkbenchException;

	boolean isLegacyWorkbench(String iMemento);

}
