package org.eclipse.ui.internal.operations;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IAdvancedUndoableOperation;
import org.eclipse.core.commands.operations.IAdvancedUndoableOperation2;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationApprover2;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.util.Util;

public class AdvancedValidationUserApprover implements IOperationApprover,
		IOperationApprover2 {
	
    public static boolean AUTOMATED_MODE = false;

	private IUndoContext context;

	private static final int EXECUTING = 1;

	private static final int UNDOING = 2;

	private static final int REDOING = 3;

	private class StatusReportingRunnable implements IRunnableWithProgress {
		IStatus status;

		int doing;

		IUndoableOperation operation;

		IAdaptable uiInfo;

		StatusReportingRunnable(IUndoableOperation operation,
				IOperationHistory history, IAdaptable uiInfo, int doing) {
			super();
			this.operation = operation;
			this.doing = doing;
			this.uiInfo = uiInfo;
		}

		@Override
		public void run(IProgressMonitor pm) {
			try {
				switch (doing) {
				case UNDOING:
					status = ((IAdvancedUndoableOperation) operation)
							.computeUndoableStatus(pm);
					break;
				case REDOING:
					status = ((IAdvancedUndoableOperation) operation)
							.computeRedoableStatus(pm);
					break;
				case EXECUTING:
					status = ((IAdvancedUndoableOperation2) operation)
							.computeExecutionStatus(pm);
					break;
				}

			} catch (ExecutionException e) {
				reportException(e, uiInfo);
				status = IOperationHistory.OPERATION_INVALID_STATUS;
			}
		}

		IStatus getStatus() {
			return status;
		}
	}

	public AdvancedValidationUserApprover(IUndoContext context) {
		super();
		this.context = context;
	}

	@Override
	public IStatus proceedRedoing(IUndoableOperation operation,
			IOperationHistory history, IAdaptable uiInfo) {
		return proceedWithOperation(operation, history, uiInfo, REDOING);
	}

	@Override
	public IStatus proceedUndoing(IUndoableOperation operation,
			IOperationHistory history, IAdaptable uiInfo) {

		return proceedWithOperation(operation, history, uiInfo, UNDOING);
	}

	@Override
	public IStatus proceedExecuting(IUndoableOperation operation,
			IOperationHistory history, IAdaptable uiInfo) {
		return proceedWithOperation(operation, history, uiInfo, EXECUTING);
	}

	private IStatus proceedWithOperation(final IUndoableOperation operation,
			final IOperationHistory history, final IAdaptable uiInfo,
			final int doing) {

		if (!operation.hasContext(context)) {
			return Status.OK_STATUS;
		}

		if (doing == EXECUTING) {
			if (!(operation instanceof IAdvancedUndoableOperation2)) {
				return Status.OK_STATUS;
			}
		} else {
			if (!(operation instanceof IAdvancedUndoableOperation)) {
				return Status.OK_STATUS;
			}
		}

		final IStatus[] status = new IStatus[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				status[0] = computeOperationStatus(operation, history, uiInfo,
						doing);

				if (!status[0].isOK()) {
					status[0] = reportAndInterpretStatus(status[0], uiInfo,
							operation, doing);
				}

			}
		});

		if (!status[0].isOK()) {
			history.operationChanged(operation);
		}
		return status[0];
	}

	private IStatus computeOperationStatus(IUndoableOperation operation,
			IOperationHistory history, IAdaptable uiInfo, int doing) {
		try {
			StatusReportingRunnable runnable = new StatusReportingRunnable(
					operation, history, uiInfo, doing);
			TimeTriggeredProgressMonitorDialog progressDialog = new TimeTriggeredProgressMonitorDialog(
					getShell(uiInfo), PlatformUI.getWorkbench()
							.getProgressService().getLongOperationTime());

			progressDialog.run(false, true, runnable);
			return runnable.getStatus();
		} catch (OperationCanceledException e) {
			return Status.CANCEL_STATUS;
		} catch (InvocationTargetException e) {
			reportException(e, uiInfo);
			return IOperationHistory.OPERATION_INVALID_STATUS;
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
	}

	private void reportException(Exception e, IAdaptable uiInfo) {
		Throwable nestedException = StatusUtil.getCause(e);
		Throwable exception = (nestedException == null) ? e : nestedException;
		String title = WorkbenchMessages.Error;
		String message = WorkbenchMessages.WorkbenchWindow_exceptionMessage;
		String exceptionMessage = exception.getMessage();
		if (exceptionMessage == null) {
			exceptionMessage = message;
		}
		IStatus status = new Status(IStatus.ERROR,
				WorkbenchPlugin.PI_WORKBENCH, 0, exceptionMessage, exception);
		WorkbenchPlugin.log(message, status);

		boolean createdShell = false;
		Shell shell = getShell(uiInfo);
		if (shell == null) {
			createdShell = true;
			shell = new Shell();
		}
		ErrorDialog.openError(shell, title, message, status);
		if (createdShell) {
			shell.dispose();
		}
	}

	private IStatus reportAndInterpretStatus(IStatus status, IAdaptable uiInfo,
			IUndoableOperation operation, int doing) {
		if (AUTOMATED_MODE) {
			if (status.getSeverity() == IStatus.WARNING) {
				return Status.OK_STATUS;
			}
			return status;
		}
		
		if (status.getSeverity() == IStatus.CANCEL) {
			return status;
		}

		boolean createdShell = false;
		IStatus reportedStatus = status;

		Shell shell = getShell(uiInfo);
		if (shell == null) {
			createdShell = true;
			shell = new Shell();
		}


		if (!(status.getSeverity() == IStatus.ERROR)) {
			String warning, title;
			switch (doing) {
			case UNDOING:
				warning = WorkbenchMessages.Operations_proceedWithNonOKUndoStatus;
				if (status.getSeverity() == IStatus.INFO) {
					title = WorkbenchMessages.Operations_undoInfo;
				} else {
					title = WorkbenchMessages.Operations_undoWarning;
				}
				break;
			case REDOING:
				warning = WorkbenchMessages.Operations_proceedWithNonOKRedoStatus;
				if (status.getSeverity() == IStatus.INFO) {
					title = WorkbenchMessages.Operations_redoInfo;
				} else {
					title = WorkbenchMessages.Operations_redoWarning;
				}
				break;
			default: // EXECUTING
				warning = WorkbenchMessages.Operations_proceedWithNonOKExecuteStatus;
				if (status.getSeverity() == IStatus.INFO) {
					title = WorkbenchMessages.Operations_executeInfo;
				} else {
					title = WorkbenchMessages.Operations_executeWarning;
				}
				break;
			}

			String message = NLS.bind(warning, new Object[] { status.getMessage(), operation.getLabel() });
			String[] buttons = new String[] { IDialogConstants.YES_LABEL,
					IDialogConstants.NO_LABEL };
			MessageDialog dialog = new MessageDialog(shell, title, null,
					message, MessageDialog.WARNING, buttons, 0);
			int dialogAnswer = dialog.open();
			if (dialogAnswer == Window.OK) {
				reportedStatus = Status.OK_STATUS;
			} else {
				reportedStatus = Status.CANCEL_STATUS;
			}
		} else {
			String title, stopped;
			switch (doing) {
			case UNDOING:
				title = WorkbenchMessages.Operations_undoProblem;
				stopped = WorkbenchMessages.Operations_stoppedOnUndoErrorStatus;
				break;
			case REDOING:
				title = WorkbenchMessages.Operations_redoProblem;
				stopped = WorkbenchMessages.Operations_stoppedOnRedoErrorStatus;
				break;
			default: // EXECUTING
				title = WorkbenchMessages.Operations_executeProblem;
				stopped = WorkbenchMessages.Operations_stoppedOnExecuteErrorStatus;

				break;
			}


			String message = NLS.bind(stopped, status.getMessage(), operation
					.getLabel());

			MessageDialog dialog = new MessageDialog(shell, title, null,
					message, MessageDialog.WARNING,
					new String[] { IDialogConstants.OK_LABEL }, 0); // ok
			dialog.open();
		}

		if (createdShell) {
			shell.dispose();
		}

		return reportedStatus;

	}

	Shell getShell(IAdaptable uiInfo) {
		if (uiInfo != null) {
			Shell shell = (Shell) Util.getAdapter(uiInfo, Shell.class);
			if (shell != null) {
				return shell;
			}
		}
		return null;
	}
}
