
package org.eclipse.ui.internal.operations;

import org.eclipse.core.commands.operations.DefaultOperationHistory;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.misc.Policy;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;

public class WorkbenchOperationSupport implements IWorkbenchOperationSupport {

	private ObjectUndoContext undoContext;
	private IOperationApprover approver;
	
	static {
		DefaultOperationHistory.DEBUG_OPERATION_HISTORY_UNEXPECTED = Policy.DEBUG_OPERATIONS;
		DefaultOperationHistory.DEBUG_OPERATION_HISTORY_OPENOPERATION = Policy.DEBUG_OPERATIONS;
		DefaultOperationHistory.DEBUG_OPERATION_HISTORY_APPROVAL = Policy.DEBUG_OPERATIONS;
		DefaultOperationHistory.DEBUG_OPERATION_HISTORY_NOTIFICATION = Policy.DEBUG_OPERATIONS && Policy.DEBUG_OPERATIONS_VERBOSE;
		DefaultOperationHistory.DEBUG_OPERATION_HISTORY_DISPOSE = Policy.DEBUG_OPERATIONS && Policy.DEBUG_OPERATIONS_VERBOSE;
	}

	public void dispose() {
		getOperationHistory().removeOperationApprover(approver);
		getOperationHistory().dispose(getUndoContext(), true, true, true);
	}

	@Override
	public IUndoContext getUndoContext() {
		if (undoContext == null) {
			undoContext = new ObjectUndoContext(PlatformUI.getWorkbench(),
					"Workbench Context"); //$NON-NLS-1$
		}
		return undoContext;
	}

	@Override
	public IOperationHistory getOperationHistory() {
		IOperationHistory history = OperationHistoryFactory.getOperationHistory();
		if (approver == null) {
			approver = new AdvancedValidationUserApprover(getUndoContext());
			history.addOperationApprover(approver);
			history.setLimit(getUndoContext(), 25);
		}
		return history;
	}

}
