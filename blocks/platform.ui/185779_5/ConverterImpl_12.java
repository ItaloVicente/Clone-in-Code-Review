package org.eclipse.e4.ui.workbench.persistence.compatibility;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.internal.persistence.WorkbenchState;
import org.eclipse.e4.ui.workbench.persistence.compatibility.impl.PerspectiveMigratorImpl;
import org.eclipse.ui.WorkbenchException;

public interface PerspectiveMigrator {

	public static PerspectiveMigrator INSTANCE = new PerspectiveMigratorImpl();

	void apply3xWorkbenchState(String iMemento) throws WorkbenchException;

	WorkbenchState convertToWorkbenchState(MApplication application);

	MApplication convertToMApplication(String iMemento) throws WorkbenchException;

	boolean isLegacyWorkbench(String iMemento);

}
