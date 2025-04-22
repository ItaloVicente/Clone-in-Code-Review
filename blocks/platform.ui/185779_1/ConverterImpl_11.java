package org.eclipse.ui.persistence;

import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.persistence.WorkbenchState;
import org.eclipse.ui.persistence.impl.PerspectivePersisterImpl;

public interface PerspectivePersister {

	public static final PerspectivePersister INSTANCE = new PerspectivePersisterImpl();

	String serializePerspectiveAndPartStates(String perspectiveName);

	void restoreWorkbenchState(String serializedState);

	void restoreWorkbenchState(WorkbenchState workbenchState);
}
