package org.eclipse.ui.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.LinearUndoViolationDetector;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPart2;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;

public final class LinearUndoViolationUserApprover extends
		LinearUndoViolationDetector {

	private IWorkbenchPart part;

	private IUndoContext context;

	public LinearUndoViolationUserApprover(IUndoContext context,
			IWorkbenchPart part) {
		super();
		this.part = part;
		this.context = context;
	}

	@Override
	protected IStatus allowLinearRedoViolation(IUndoableOperation operation,
			IUndoContext context, IOperationHistory history, IAdaptable uiInfo) {

		if (this.context != context) {
			return Status.OK_STATUS;
		}

		final String message = NLS.bind(
				WorkbenchMessages.Operations_linearRedoViolation,
				getTitle(part), operation.getLabel());
		final boolean [] proceed = new boolean[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				part.setFocus();
				proceed[0] = MessageDialog.openQuestion(part.getSite()
						.getShell(), getTitle(part), message);
			}
		});

		if (proceed[0]) {
			while (operation != history.getRedoOperation(context)) {
				try {
					IStatus status = history.redo(context, null, uiInfo);
					if (!status.isOK()) {
						history.dispose(context, false, true, false);
						return Status.CANCEL_STATUS;
					}
				} catch (ExecutionException e) {
					history.dispose(context, false, true, false);
					return Status.CANCEL_STATUS;
				}
			}
			return Status.OK_STATUS;
		}

		return Status.CANCEL_STATUS;
	}

	@Override
	protected IStatus allowLinearUndoViolation(IUndoableOperation operation,
			IUndoContext context, IOperationHistory history, IAdaptable uiInfo) {

		if (this.context != context) {
			return Status.OK_STATUS;
		}

		final String message = NLS.bind(
				WorkbenchMessages.Operations_linearUndoViolation,
				getTitle(part), operation.getLabel());
		final boolean [] proceed = new boolean[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				part.setFocus();
				proceed[0] = MessageDialog.openQuestion(part.getSite()
						.getShell(), getTitle(part), message);
			}
		});

		if (proceed[0]) {
			while (operation != history.getUndoOperation(context)) {
				try {
					IStatus status = history.undo(context, null, uiInfo);
					if (!status.isOK()) {
						history.dispose(context, true, false, false);
						return Status.CANCEL_STATUS;
					}
				} catch (ExecutionException e) {
					history.dispose(context, true, false, false);
					return Status.CANCEL_STATUS;
				}
			}
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	private String getTitle(IWorkbenchPart part) {
		String title;
		if (part instanceof IWorkbenchPart2) {
			title = ((IWorkbenchPart2) part).getPartName();
		} else {
			title = part.getTitle();
		}
		if (title == null) {
			title = ""; //$NON-NLS-1$
		}
		return title;
	}
}
