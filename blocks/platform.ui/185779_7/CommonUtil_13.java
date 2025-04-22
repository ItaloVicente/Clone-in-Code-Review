package org.eclipse.e4.ui.workbench.persistence;

import org.eclipse.e4.ui.workbench.internal.persistence.WorkbenchState;
import org.eclipse.e4.ui.workbench.persistence.impl.PerspectivePersisterImpl;
import org.eclipse.ui.IWorkbenchWindow;

public interface PerspectivePersister {

	public static final PerspectivePersister INSTANCE = new PerspectivePersisterImpl();

	String serializePerspectiveAndPartStates(String perspectiveName);

	void restoreWorkbenchState(String serializedState);

	void restoreWorkbenchState(WorkbenchState workbenchState);
}
