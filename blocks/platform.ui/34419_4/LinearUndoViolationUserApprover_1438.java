
package org.eclipse.ui.operations;

import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;

public interface IWorkbenchOperationSupport {

	public IUndoContext getUndoContext();

	public IOperationHistory getOperationHistory();

}
