package org.eclipse.ui.internal.progress;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.IJobChangeListener;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IPreferenceConstants;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.progress.WorkbenchJob;

class ProgressMonitorFocusJobDialog extends ProgressMonitorJobsDialog {
	Job job;
	private boolean showDialog;

	public ProgressMonitorFocusJobDialog(Shell parentShell) {
		super(parentShell == null ? ProgressManagerUtil.getNonModalShell()
				: parentShell);
		setShellStyle(getDefaultOrientation() | SWT.BORDER | SWT.TITLE
				| SWT.RESIZE | SWT.MAX | SWT.MODELESS);
		setCancelable(true);
		enableDetailsButton = true;
	}

	@Override
	protected void cancelPressed() {
		job.cancel();
		super.cancelPressed();
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(job.getName());

	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button runInWorkspace = createButton(
				parent,
				IDialogConstants.CLOSE_ID,
				ProgressMessages.ProgressMonitorFocusJobDialog_RunInBackgroundButton,
				true);
		runInWorkspace.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Rectangle shellPosition = getShell().getBounds();
				job.setProperty(IProgressConstants.PROPERTY_IN_DIALOG,
						Boolean.FALSE);
				finishedRun();
				ProgressManagerUtil.animateDown(shellPosition);
			}
		});
		runInWorkspace.setCursor(arrowCursor);

		cancel = createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
		cancel.setCursor(arrowCursor);

		createDetailsButton(parent);
	}

	private IJobChangeListener createCloseListener() {
		return new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				event.getJob().removeJobChangeListener(this);
				if (!PlatformUI.isWorkbenchRunning()) {
					return;
				}
				if (getShell() == null) {
					return;
				}
				WorkbenchJob closeJob = new WorkbenchJob(
						ProgressMessages.ProgressMonitorFocusJobDialog_CLoseDialogJob) {
					@Override
					public IStatus runInUIThread(IProgressMonitor monitor) {
						Shell currentShell = getShell();
						if (currentShell == null || currentShell.isDisposed()) {
							return Status.CANCEL_STATUS;
						}
						finishedRun();
						return Status.OK_STATUS;
					}
				};
				closeJob.setSystem(true);
				closeJob.schedule();
			}
		};
	}

	private IProgressMonitorWithBlocking getBlockingProgressMonitor() {
		return new IProgressMonitorWithBlocking() {
			@Override
			public void beginTask(String name, int totalWork) {
				final String finalName = name;
				final int finalWork = totalWork;
				runAsync(new Runnable() {
					@Override
					public void run() {
						getProgressMonitor().beginTask(finalName, finalWork);
					}
				});
			}

			@Override
			public void clearBlocked() {
				runAsync(new Runnable() {
					@Override
					public void run() {
						((IProgressMonitorWithBlocking) getProgressMonitor())
								.clearBlocked();
					}
				});
			}

			@Override
			public void done() {
				runAsync(new Runnable() {
					@Override
					public void run() {
						getProgressMonitor().done();
					}
				});
			}

			@Override
			public void internalWorked(double work) {
				final double finalWork = work;
				runAsync(new Runnable() {
					@Override
					public void run() {
						getProgressMonitor().internalWorked(finalWork);
					}
				});
			}

			@Override
			public boolean isCanceled() {
				return getProgressMonitor().isCanceled();
			}

			private void runAsync(final Runnable runnable) {

				if (alreadyClosed) {
					return;
				}
				Shell currentShell = getShell();

				Display display;
				if (currentShell == null) {
					display = Display.getDefault();
				} else {
					if (currentShell.isDisposed())// Don't bother if it has
						return;
					display = currentShell.getDisplay();
				}

				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						if (alreadyClosed) {
							return;// Check again as the async may come too
						}
						Shell shell = getShell();
						if (shell != null && shell.isDisposed())
							return;

						runnable.run();
					}
				});
			}

			@Override
			public void setBlocked(IStatus reason) {
				final IStatus finalReason = reason;
				runAsync(new Runnable() {
					@Override
					public void run() {
						((IProgressMonitorWithBlocking) getProgressMonitor())
								.setBlocked(finalReason);
					}
				});
			}

			@Override
			public void setCanceled(boolean value) {
			}

			@Override
			public void setTaskName(String name) {
				final String finalName = name;
				runAsync(new Runnable() {
					@Override
					public void run() {
						getProgressMonitor().setTaskName(finalName);
					}
				});
			}

			@Override
			public void subTask(String name) {
				final String finalName = name;
				runAsync(new Runnable() {
					@Override
					public void run() {
						getProgressMonitor().subTask(finalName);
					}
				});
			}

			@Override
			public void worked(int work) {
				internalWorked(work);
			}
		};
	}

	@Override
	public int open() {
		int result = super.open();

		IJobChangeListener listener = createCloseListener();
		job.addJobChangeListener(listener);
		if (job.getState() == Job.NONE) {
			job.removeJobChangeListener(listener);
			finishedRun();
			cleanUpFinishedJob();
		}

		return result;
	}

	public void show(Job jobToWatch, final Shell originatingShell) {
		job = jobToWatch;
		job.setProperty(IProgressConstants.PROPERTY_IN_DIALOG, Boolean.TRUE);

		ProgressManager.getInstance().progressFor(job).addProgressListener(
				getBlockingProgressMonitor());

		setOpenOnRun(false);
		aboutToRun();

		final Object jobIsDone = new Object();
		final JobChangeAdapter jobListener = new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				synchronized (jobIsDone) {
					jobIsDone.notify();
				}
			}
		};
		job.addJobChangeListener(jobListener);

		BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(),
				new Runnable() {
					@Override
					public void run() {
						try {
							synchronized (jobIsDone) {
								if (job.getState() != Job.NONE) {
									jobIsDone.wait(ProgressManagerUtil.SHORT_OPERATION_TIME);
								}
							}
						} catch (InterruptedException e) {
						}
					}
				});
		job.removeJobChangeListener(jobListener);

		WorkbenchJob openJob = new WorkbenchJob(
				ProgressMessages.ProgressMonitorFocusJobDialog_UserDialogJob) {
			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

				if (job.getState() == Job.NONE) {
					finishedRun();
					cleanUpFinishedJob();
					return Status.CANCEL_STATUS;
				}

				if (!ProgressManagerUtil.safeToOpen(
						ProgressMonitorFocusJobDialog.this, originatingShell)) {
					return Status.CANCEL_STATUS;
				}

				if (getParentShell() != null && getParentShell().isDisposed()) {
					return Status.CANCEL_STATUS;
				}

				open();

				return Status.OK_STATUS;
			}
		};
		openJob.setSystem(true);
		openJob.schedule();

	}

	private void cleanUpFinishedJob() {
		ProgressManager.getInstance().checkForStaleness(job);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Control area = super.createDialogArea(parent);
		getProgressMonitor().setTaskName(
				ProgressManager.getInstance().getJobInfo(this.job)
						.getDisplayString());
		return area;
	}

	@Override
	protected void createExtendedDialogArea(Composite parent) {

		showDialog = WorkbenchPlugin.getDefault().getPreferenceStore()
				.getBoolean(IPreferenceConstants.RUN_IN_BACKGROUND);
		final Button showUserDialogButton = new Button(parent, SWT.CHECK);
		showUserDialogButton
				.setText(WorkbenchMessages.WorkbenchPreference_RunInBackgroundButton);
		showUserDialogButton
				.setToolTipText(WorkbenchMessages.WorkbenchPreference_RunInBackgroundToolTip);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = GridData.FILL;
		showUserDialogButton.setLayoutData(gd);

		showUserDialogButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showDialog = showUserDialogButton.getSelection();
			}
		});

		super.createExtendedDialogArea(parent);
	}

	@Override
	public boolean close() {
		if (getReturnCode() != CANCEL)
			WorkbenchPlugin.getDefault().getPreferenceStore().setValue(
					IPreferenceConstants.RUN_IN_BACKGROUND, showDialog);

		return super.close();
	}
}
