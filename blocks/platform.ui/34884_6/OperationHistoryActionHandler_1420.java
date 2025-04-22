package org.eclipse.ui.operations;

import java.util.ArrayList;

import org.eclipse.core.commands.operations.IAdvancedUndoableOperation;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.util.Util;

public final class NonLocalUndoUserApprover implements IOperationApprover {

	private IUndoContext context;

	private IEditorPart part;

	private Object[] elements;

	private Class affectedObjectsClass;

	private ArrayList elementsAndAdapters;

	public NonLocalUndoUserApprover(IUndoContext context, IEditorPart part,
			Object[] affectedObjects, Class preferredComparisonClass) {
		super();
		this.context = context;
		this.part = part;
		this.affectedObjectsClass = preferredComparisonClass;
		this.elements = affectedObjects;
	}

	@Override
	public IStatus proceedRedoing(IUndoableOperation operation,
			IOperationHistory history, IAdaptable uiInfo) {

		if (!requiresApproval(operation, uiInfo)) {
			return Status.OK_STATUS;
		}

		String message = NLS.bind(
				WorkbenchMessages.Operations_nonLocalRedoWarning, operation
						.getLabel(), part.getEditorInput().getName());
		return proceedWithOperation(operation, message, WorkbenchMessages.Operations_discardRedo, WorkbenchMessages.Workbench_redoToolTip);
	}

	@Override
	public IStatus proceedUndoing(IUndoableOperation operation,
			IOperationHistory history, IAdaptable uiInfo) {

		if (!requiresApproval(operation, uiInfo)) {
			return Status.OK_STATUS;
		}

		String message = NLS.bind(
				WorkbenchMessages.Operations_nonLocalUndoWarning, operation
						.getLabel(), part.getEditorInput().getName());
		return proceedWithOperation(operation, message, WorkbenchMessages.Operations_discardUndo, WorkbenchMessages.Workbench_undoToolTip);

	}

	private IStatus proceedWithOperation(IUndoableOperation operation,
			final String message, final String discardButton, final String title) {

		if (!(operation instanceof IAdvancedUndoableOperation)) {
			return Status.OK_STATUS;
		}

		Object[] modifiedElements = ((IAdvancedUndoableOperation) operation)
				.getAffectedObjects();


		boolean local;
		if (modifiedElements == null) {
			local = false;
		} else {
			local = true;
			for (int i = 0; i < modifiedElements.length; i++) {
				Object modifiedElement = modifiedElements[i];
				if (!elementsContains(modifiedElement)) {
					local = false;
					if (affectedObjectsClass != null) {
						Object adapter = Util.getAdapter(modifiedElement, 
								affectedObjectsClass);
						if (adapter != null && elementsContains(adapter)) {
							local = true;
						}
					}
					if (!local) {
						break;
					}
				}
			}
		}
		if (local) {
			return Status.OK_STATUS;
		}

		final int[] answer = new int[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog dialog = new MessageDialog(part.getSite().getShell(), title,
						null, message, MessageDialog.QUESTION, new String[] { IDialogConstants.OK_LABEL,
		                        discardButton, IDialogConstants.CANCEL_LABEL }, 0); // yes is the default
		        answer[0] = dialog.open();
		}});
		switch (answer[0]) {
		case 0:
			return Status.OK_STATUS;
		case 1:
			return IOperationHistory.OPERATION_INVALID_STATUS;
		default:
			return Status.CANCEL_STATUS;
		}
	}

	private boolean requiresApproval(IUndoableOperation operation,
			IAdaptable uiInfo) {
		if (!(operation.hasContext(context))) {
			return false;
		}

		if (operation.getContexts().length == 1) {
			return false;
		}

		if (uiInfo != null) {
			IUndoContext originatingContext = (IUndoContext) Util.getAdapter(uiInfo, 
					IUndoContext.class);
			if (originatingContext != null
					&& !(originatingContext.matches(context))) {
				return false;
			}
		}

		return true;
	}

	private boolean elementsContains(Object someObject) {
		if (elements == null) {
			return false;
		}
		if (elementsAndAdapters == null) {
			elementsAndAdapters = new ArrayList(elements.length);
			for (int i = 0; i < elements.length; i++) {
				Object element = elements[i];
				elementsAndAdapters.add(element);
				if (affectedObjectsClass != null
						&& !affectedObjectsClass.isInstance(element)) {
					Object adapter = Util.getAdapter(element, affectedObjectsClass);
					if (adapter != null) {
						elementsAndAdapters.add(adapter);
					}
				}
			}
		}
		for (int i = 0; i < elementsAndAdapters.size(); i++) {
			if (elementsAndAdapters.get(i).equals(someObject)) {
				return true;
			}
		}
		return false;
	}
}
