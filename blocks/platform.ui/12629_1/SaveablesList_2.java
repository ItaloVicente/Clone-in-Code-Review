package org.eclipse.e4.ui.internal.workbench.swt;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.IExceptionHandler;
import org.eclipse.e4.ui.workbench.swt.IJobRunnable;
import org.eclipse.e4.ui.workbench.swt.ISaveablePart;
import org.eclipse.e4.ui.workbench.swt.ISaveablePart2;
import org.eclipse.e4.ui.workbench.swt.ISaveablesLifecycleListener;
import org.eclipse.e4.ui.workbench.swt.ISaveablesSource;
import org.eclipse.e4.ui.workbench.swt.Saveable;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;

public class SaveableHelper {

	public static final int USER_RESPONSE = -1;

	private static int AutomatedResponse = USER_RESPONSE;

	public static void testSetAutomatedResponse(int response) {
		AutomatedResponse = response;
	}

	public static int testGetAutomatedResponse() {
		return AutomatedResponse;
	}

	static boolean savePart(final ISaveablePart saveable, final MPart part,
			final IRunnableContext runnableContext,
			final IShellProvider shellProvider, boolean confirm) {
		if (!saveable.isDirty()) {
			return true;
		}

		if (confirm) {
			int choice = AutomatedResponse;
			if (choice == USER_RESPONSE) {
				if (saveable instanceof ISaveablePart2) {
					choice = ((ISaveablePart2) saveable).promptToSaveOnClose();
				}
				if (choice == USER_RESPONSE || choice == ISaveablePart2.DEFAULT) {
					String message = NLS
							.bind(WorkbenchMessages.EditorManager_saveChangesQuestion,
									part.getLocalizedLabel());
					String[] buttons = new String[] {
							IDialogConstants.YES_LABEL,
							IDialogConstants.NO_LABEL,
							IDialogConstants.CANCEL_LABEL };
					MessageDialog d = new MessageDialog(
							shellProvider.getShell(),
							WorkbenchMessages.Save_Resource, null, message,
							MessageDialog.QUESTION, buttons, 0) {
						protected int getShellStyle() {
							return super.getShellStyle() | SWT.SHEET;
						}
					};
					choice = d.open();
				}
			}

			switch (choice) {
			case ISaveablePart2.YES: // yes
				break;
			case ISaveablePart2.NO: // no
				return true;
			default:
			case ISaveablePart2.CANCEL: // cancel
				return false;
			}
		}

		if (saveable instanceof ISaveablesSource) {
			return saveModels((ISaveablesSource) saveable, part,
					runnableContext, shellProvider, confirm);
		}

		IRunnableWithProgress progressOp = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(
						monitor, part.getContext().get(IExceptionHandler.class));
				saveable.doSave(monitorWrap);
			}
		};

		return runProgressMonitorOperation(WorkbenchMessages.Save, progressOp,
				runnableContext, shellProvider);
	}

	private static boolean saveModels(ISaveablesSource modelSource,
			final MPart part, final IRunnableContext runnableContext,
			final IShellProvider shellProvider, final boolean confirm) {
		Saveable[] selectedModels = modelSource.getActiveSaveables();
		final ArrayList dirtyModels = new ArrayList();
		for (int i = 0; i < selectedModels.length; i++) {
			Saveable model = selectedModels[i];
			if (model.isDirty()) {
				dirtyModels.add(model);
			}
		}
		if (dirtyModels.isEmpty()) {
			return true;
		}

		IRunnableWithProgress progressOp = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(
						monitor, part.getContext().get(IExceptionHandler.class));
				monitorWrap.beginTask(WorkbenchMessages.Save,
						dirtyModels.size());
				try {
					for (Iterator i = dirtyModels.iterator(); i.hasNext();) {
						Saveable model = (Saveable) i.next();
						if (!model.isDirty()) {
							monitor.worked(1);
							continue;
						}
						doSaveModel(model, new SubProgressMonitor(monitorWrap,
								1), shellProvider, confirm);
						if (monitor.isCanceled()) {
							break;
						}
					}
				} finally {
					monitorWrap.done();
				}
			}
		};

		return runProgressMonitorOperation(WorkbenchMessages.Save, progressOp,
				runnableContext, shellProvider);
	}

	static int savePart(final ISaveablePart2 saveable, final MPart part,
			final IRunnableContext runnableContext,
			final IShellProvider shellProvider, boolean confirm) {
		if (!saveable.isDirty()) {
			return ISaveablePart2.YES;
		}

		if (confirm) {
			int choice = AutomatedResponse;
			if (choice == USER_RESPONSE) {
				choice = saveable.promptToSaveOnClose();
			}

			if (choice != ISaveablePart2.YES) {
				return (choice == USER_RESPONSE ? ISaveablePart2.DEFAULT
						: choice);
			}
		}

		IRunnableWithProgress progressOp = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) {
				IProgressMonitor monitorWrap = new EventLoopProgressMonitor(
						monitor, part.getContext().get(IExceptionHandler.class));
				saveable.doSave(monitorWrap);
			}
		};

		if (!runProgressMonitorOperation(WorkbenchMessages.Save, progressOp,
				runnableContext, shellProvider)) {
			return ISaveablePart2.CANCEL;
		}
		return ISaveablePart2.YES;
	}

	static boolean runProgressMonitorOperation(String opName,
			final IRunnableWithProgress progressOp,
			final IRunnableContext runnableContext,
			final IShellProvider shellProvider) {
		final boolean[] success = new boolean[] { false };
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				progressOp.run(monitor);
				if (!monitor.isCanceled())
					success[0] = true;
			}
		};

		try {
			runnableContext.run(false, true, runnable);
		} catch (InvocationTargetException e) {
			String title = NLS.bind(
					WorkbenchMessages.EditorManager_operationFailed, opName);
			Throwable targetExc = e.getTargetException();
		} catch (InterruptedException e) {
		} catch (OperationCanceledException e) {
		}
		return success[0];
	}

	public static boolean needsSave(ISaveablesSource modelSource) {
		Saveable[] selectedModels = modelSource.getActiveSaveables();
		for (int i = 0; i < selectedModels.length; i++) {
			Saveable model = selectedModels[i];
			if (model.isDirty()
					&& !((InternalSaveable) model).isSavingInBackground()) {
				return true;
			}
		}
		return false;
	}

	public static void doSaveModel(final Saveable model,
			IProgressMonitor progressMonitor,
			final IShellProvider shellProvider, boolean blockUntilSaved) {
		try {
			Job backgroundSaveJob = ((InternalSaveable) model)
					.getBackgroundSaveJob();
			if (backgroundSaveJob != null) {
				boolean canceled = waitForBackgroundSaveJob(model);
				if (canceled) {
					progressMonitor.setCanceled(true);
					return;
				}
				if (!model.isDirty()) {
					return;
				}
			}
			final IJobRunnable[] backgroundSaveRunnable = new IJobRunnable[1];
			try {
				SubMonitor subMonitor = SubMonitor.convert(progressMonitor, 3);
				backgroundSaveRunnable[0] = model.doSave(
						subMonitor.newChild(2), shellProvider);
				if (backgroundSaveRunnable[0] == null) {
					return;
				}
				if (blockUntilSaved) {
					IStatus result = backgroundSaveRunnable[0].run(subMonitor
							.newChild(1));
					if (!result.isOK()) {
						progressMonitor.setCanceled(true);
					}
					return;
				}
				Job saveJob = new Job(NLS.bind(
						WorkbenchMessages.EditorManager_backgroundSaveJobName,
						model.getName())) {
					public boolean belongsTo(Object family) {
						if (family instanceof DynamicFamily) {
							return ((DynamicFamily) family).contains(model);
						}
						return family.equals(model);
					}

					protected IStatus run(IProgressMonitor monitor) {
						return backgroundSaveRunnable[0].run(monitor);
					}
				};
				((InternalSaveable) model).setBackgroundSaveJob(saveJob);
				SaveablesList saveablesList = (SaveablesList) PlatformUI
						.getWorkbench().getService(
								ISaveablesLifecycleListener.class);
				final IWorkbenchPart[] parts = saveablesList
						.getPartsForSaveable(model);

				for (int i = 0; i < parts.length; i++) {
					IWorkbenchPart workbenchPart = parts[i];
					IWorkbenchSiteProgressService progressService = (IWorkbenchSiteProgressService) workbenchPart
							.getSite().getAdapter(
									IWorkbenchSiteProgressService.class);
					progressService.showBusyForFamily(model);
				}
				model.disableUI(parts, blockUntilSaved);
				saveJob.addJobChangeListener(new JobChangeAdapter() {
					public void done(final IJobChangeEvent event) {
						((InternalSaveable) model).setBackgroundSaveJob(null);
						shellProvider.getShell().getDisplay()
								.asyncExec(new Runnable() {
									public void run() {
										notifySaveAction(parts);
										model.enableUI(parts);
									}
								});
					}
				});
				saveJob.schedule();
				notifySaveAction(parts);
			} catch (CoreException e) {
				StatusUtil.handleStatus(e.getStatus(), StatusManager.SHOW,
						shellProvider.getShell());
				progressMonitor.setCanceled(true);
			}
		} finally {
			progressMonitor.done();
		}
	}

	private static void notifySaveAction(final IWorkbenchPart[] parts) {
		Set wwindows = new HashSet();
		for (int i = 0; i < parts.length; i++) {
			wwindows.add(parts[i].getSite().getWorkbenchWindow());
		}
		for (Iterator it = wwindows.iterator(); it.hasNext();) {
			WorkbenchWindow wwin = (WorkbenchWindow) it.next();
			wwin.fireBackgroundSaveStarted();
		}
	}

	private static boolean waitForBackgroundSaveJob(final Saveable model) {
		List models = new ArrayList();
		models.add(model);
		return waitForBackgroundSaveJobs(models);
	}

	public static boolean waitForBackgroundSaveJobs(final List modelsToSave) {
		try {
			PlatformUI.getWorkbench().getProgressService()
					.busyCursorWhile(new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InterruptedException {
							Job.getJobManager().join(
									new DynamicFamily(modelsToSave), monitor);
						}
					});
		} catch (InvocationTargetException e) {
			StatusUtil.handleStatus(e, StatusManager.SHOW | StatusManager.LOG);
		} catch (InterruptedException e) {
			return true;
		}
		for (Iterator it = modelsToSave.iterator(); it.hasNext();) {
			Saveable model = (Saveable) it.next();
			if (!model.isDirty()) {
				it.remove();
			}
		}
		return false;
	}

	private static class DynamicFamily extends HashSet {
		private static final long serialVersionUID = 1L;

		public DynamicFamily(Collection collection) {
			super(collection);
		}
	}

}
