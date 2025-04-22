package org.eclipse.ui.operations;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IAdvancedUndoableOperation2;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.operations.TimeTriggeredProgressMonitorDialog;
import org.eclipse.ui.internal.util.Util;
import org.eclipse.ui.part.MultiPageEditorSite;
import org.eclipse.ui.statushandlers.StatusManager;

public abstract class OperationHistoryActionHandler extends Action implements
		ActionFactory.IWorkbenchAction, IAdaptable {

	private static final int MAX_LABEL_LENGTH = 33;

	private class PartListener implements IPartListener {
		@Override
		public void partActivated(IWorkbenchPart part) {
		}

		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
		}

		@Override
		public void partClosed(IWorkbenchPart part) {
			if (site != null && part.equals(site.getPart())) {
				dispose();
			} else if ((site instanceof MultiPageEditorSite)
					&& (part.equals(((MultiPageEditorSite) site)
							.getMultiPageEditor()))) {
				dispose();
			}
		}

		@Override
		public void partDeactivated(IWorkbenchPart part) {
		}

		@Override
		public void partOpened(IWorkbenchPart part) {
		}

	}

	private class HistoryListener implements IOperationHistoryListener {
		@Override
		public void historyNotification(final OperationHistoryEvent event) {
			IWorkbenchWindow workbenchWindow = getWorkbenchWindow();
			if (workbenchWindow == null)
				return;
			
			Display display = workbenchWindow.getWorkbench().getDisplay();
			if (display == null)
				return;
			
			switch (event.getEventType()) {
			case OperationHistoryEvent.OPERATION_ADDED:
			case OperationHistoryEvent.OPERATION_REMOVED:
			case OperationHistoryEvent.UNDONE:
			case OperationHistoryEvent.REDONE:
				if (event.getOperation().hasContext(undoContext)) {
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							update();
						}
					});
				}
				break;
			case OperationHistoryEvent.OPERATION_NOT_OK:
				if (event.getOperation().hasContext(undoContext)) {
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if (pruning) {
								IStatus status = event.getStatus();
								if (status == null
										|| status.getSeverity() != IStatus.CANCEL) {
									flush();
								}
								update();
							} else {
								update();
							}
						}
					});
				}
				break;
			case OperationHistoryEvent.OPERATION_CHANGED:
				if (event.getOperation() == getOperation()) {
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							update();
						}
					});
				}
				break;
			}
		}
	}

	private boolean pruning = false;

	private IPartListener partListener = new PartListener();

	private IOperationHistoryListener historyListener = new HistoryListener();

	private TimeTriggeredProgressMonitorDialog progressDialog;

	private IUndoContext undoContext = null;

	IWorkbenchPartSite site;

	OperationHistoryActionHandler(IWorkbenchPartSite site, IUndoContext context) {
		super(""); //$NON-NLS-1$
		this.site = site;
		undoContext = context;
		site.getPage().addPartListener(partListener);
		getHistory().addOperationHistoryListener(historyListener);
		update();
	}

	@Override
	public void dispose() {

		IOperationHistory history = getHistory();
		if (history != null) {
			history.removeOperationHistoryListener(historyListener);
		}

		if (isInvalid()) {
			return;
		}

		site.getPage().removePartListener(partListener);
		site = null;
		progressDialog = null;
		undoContext = null;
	}

	abstract void flush();

	abstract String getCommandString();

	abstract String getTooltipString();

	abstract String getSimpleCommandString();

	abstract String getSimpleTooltipString();

	IOperationHistory getHistory() {
		if (PlatformUI.getWorkbench() == null) {
			return null;
		}

		return PlatformUI.getWorkbench().getOperationSupport()
				.getOperationHistory();
	}

	abstract IUndoableOperation getOperation();

	@Override
	public final void run() {
		if (isInvalid()) {
			return;
		}

		Shell parent = getWorkbenchWindow().getShell();
		progressDialog = new TimeTriggeredProgressMonitorDialog(parent,
				getWorkbenchWindow().getWorkbench().getProgressService()
						.getLongOperationTime());
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor pm)
					throws InvocationTargetException {
				try {
					runCommand(pm);
				} catch (ExecutionException e) {
					if (pruning) {
						flush();
					}
					throw new InvocationTargetException(e);
				}
			}
		};
		try {
			boolean runInBackground = false;
			if (getOperation() instanceof IAdvancedUndoableOperation2) {
				runInBackground = ((IAdvancedUndoableOperation2) getOperation())
						.runInBackground();
			}
			progressDialog.run(runInBackground, true, runnable);
		} catch (InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if (t == null) {
				reportException(e);
			} else {
				reportException(t);
			}
		} catch (InterruptedException e) {
		} catch (OperationCanceledException e) {
		} finally {
			progressDialog = null;
		}
	}

	abstract IStatus runCommand(IProgressMonitor pm) throws ExecutionException;

	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IUndoContext.class)) {
			return undoContext;
		}
		if (adapter.equals(IProgressMonitor.class)) {
			if (progressDialog != null) {
				return progressDialog.getProgressMonitor();
			}
		}
		if (site != null) {
			if (adapter.equals(Shell.class)) {
				return getWorkbenchWindow().getShell();
			}
			if (adapter.equals(IWorkbenchWindow.class)) {
				return getWorkbenchWindow();
			}
			if (adapter.equals(IWorkbenchPart.class)) {
				return site.getPart();
			}
			IWorkbenchPart part = site.getPart();
			if (part != null) {
				return Util.getAdapter(part, adapter);
			}
		}
		return null;
	}

	private IWorkbenchWindow getWorkbenchWindow() {
		if (site != null) {
			return site.getWorkbenchWindow();
		}
		return null;
	}

	abstract boolean shouldBeEnabled();

	public void setContext(IUndoContext context) {
		if (context == undoContext) {
			return;
		}
		undoContext = context;
		update();
	}

	public void setPruneHistory(boolean prune) {
		pruning = prune;
	}

	public void update() {
		if (isInvalid()) {
			return;
		}

		boolean enabled = shouldBeEnabled();
		String text, tooltipText;
		if (enabled) {
			tooltipText = NLS.bind(getTooltipString(), getOperation()
					.getLabel());
			text = NLS.bind(getCommandString(),
					LegacyActionTools.escapeMnemonics(shortenText(getOperation().getLabel()))) + '@';
		} else {
			tooltipText = NLS.bind(
					WorkbenchMessages.Operations_undoRedoCommandDisabled,
					getSimpleTooltipString());
			text = getSimpleCommandString();
			if (undoContext != null && pruning) {
				flush();
			}
		}
		setText(text);
		setToolTipText(tooltipText);
		setEnabled(enabled);
	}

	private String shortenText(String message) {
		int length = message.length();
		if (length > MAX_LABEL_LENGTH) {
			StringBuffer result = new StringBuffer();
			int end = MAX_LABEL_LENGTH / 2 - 1;
			result.append(message.substring(0, end));
			result.append("..."); //$NON-NLS-1$
			result.append(message.substring(length - end));
			return result.toString();
		}
		return message;
	}

	final void reportException(Throwable t) {
		Throwable nestedException = StatusUtil.getCause(t);
		Throwable exception = (nestedException == null) ? t : nestedException;

		String exceptionMessage = exception.getMessage();
		if (exceptionMessage == null) {
			exceptionMessage = WorkbenchMessages.WorkbenchWindow_exceptionMessage;
		}
		IStatus status = StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH,
				exceptionMessage, exception);

		WorkbenchPlugin.log(exceptionMessage, status);
		StatusUtil.handleStatus(status, StatusManager.SHOW,
				getWorkbenchWindow().getShell());
	}

	final boolean isInvalid() {
		return undoContext == null || site == null;
	}

	final IUndoContext getUndoContext() {
		return undoContext;
	}
}
