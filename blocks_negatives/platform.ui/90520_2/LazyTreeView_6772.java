/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.examples.jobs.views;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.examples.jobs.TestJob;
import org.eclipse.ui.examples.jobs.TestJobRule;
import org.eclipse.ui.examples.jobs.UITestJob;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.progress.IProgressService;

/**
 * A view that allows a user to create jobs of various types, and interact with
 * and test other job-related APIs.
 */
public class JobsView extends ViewPart {
	private Combo durationField;
	private Button lockField, failureField, threadField, systemField,
			userField, groupField, rescheduleField, keepField, keepOneField,
			unknownField, gotoActionField;
	private Text quantityField, delayField, rescheduleDelay;
	private Button schedulingRuleField;
	private Button noPromptField;

	protected void busyCursorWhile() {
		try {
			final long duration = getDuration();
			final boolean shouldLock = lockField.getSelection();
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor) {
							if (shouldLock)
								doRunInWorkspace(duration, monitor);
							else
								doRun(duration, monitor);
						}

					});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
		}
	}

	protected void createJobs() {
		int jobCount = Integer.parseInt(quantityField.getText());
		boolean ui = threadField.getSelection();
		long duration = getDuration();
		boolean lock = lockField.getSelection();
		boolean failure = failureField.getSelection();
		boolean noPrompt = noPromptField.getSelection();
		boolean system = systemField.getSelection();
		boolean useGroup = groupField.getSelection();
		boolean unknown = unknownField.getSelection();
		boolean user = userField.getSelection();
		boolean reschedule = rescheduleField.getSelection();
		final long rescheduleWait = Long.parseLong(rescheduleDelay.getText());
		boolean keep = keepField.getSelection();
		boolean keepOne = keepOneField.getSelection();
		boolean gotoAction = gotoActionField.getSelection();
		boolean schedulingRule = schedulingRuleField.getSelection();

		int groupIncrement = IProgressMonitor.UNKNOWN;
		IProgressMonitor group = new NullProgressMonitor();
		int total = IProgressMonitor.UNKNOWN;

		if (jobCount > 1) {
			total = 100;
			groupIncrement = 100 / jobCount;
		}

		if (useGroup) {
			group = Job.getJobManager().createProgressGroup();
			group.beginTask("Group", total); //$NON-NLS-1$
		}

		long delay = Integer.parseInt(delayField.getText());
		for (int i = 0; i < jobCount; i++) {
			Job result;
			if (ui)
				result = new UITestJob(duration, lock, failure, unknown);
			else
				result = new TestJob(duration, lock, failure, unknown,
						reschedule, rescheduleWait);

			result.setProperty(IProgressConstants.KEEP_PROPERTY, Boolean
					.valueOf(keep));
			result.setProperty(IProgressConstants.KEEPONE_PROPERTY, Boolean
					.valueOf(keepOne));
			result.setProperty(
					IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY,
					Boolean.valueOf(noPrompt));
			if (gotoAction)
				result.setProperty(IProgressConstants.ACTION_PROPERTY,
							@Override
							public void run() {
								MessageDialog
										.openInformation(
												getSite().getShell(),
												"Goto Action", "The job can have an action associated with it"); //$NON-NLS-1$ //$NON-NLS-2$
							}
						});

			result.setProgressGroup(group, groupIncrement);
			result.setSystem(system);
			result.setUser(user);

			if (schedulingRule)
				result.setRule(new TestJobRule(i));
			result.schedule(delay);
		}
	}

	/**
	 * @see ViewPart#createPartControl(Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = false;
		body.setLayout(layout);

		createEntryFieldGroup(body);
		createPushButtonGroup(body);
		createCheckboxGroup(body);
	}

	/**
	 * Create all push button parts for the jobs view.
	 *
	 * @param parent
	 */
	private void createPushButtonGroup(Composite parent) {
		Composite group = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		Button create = new Button(group, SWT.PUSH);
		create
		create.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		create.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				createJobs();
			}
		});

		Button touch = new Button(group, SWT.PUSH);
		touch.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		touch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				touchWorkspace();
			}
		});
		Button busyWhile = new Button(group, SWT.PUSH);
		busyWhile.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		busyWhile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				busyCursorWhile();
			}
		});
		Button noFork = new Button(group, SWT.PUSH);
		noFork.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		noFork.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				progressNoFork();
			}
		});

		Button exception = new Button(group, SWT.PUSH);
		exception.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		exception.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				jobWithRuntimeException();
			}
		});

		Button join = new Button(group, SWT.PUSH);
		join.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		join.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				joinTestJobs();
			}
		});

		Button window = new Button(group, SWT.PUSH);
		window
		window.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		window.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				runnableInWindow();
			}
		});

		Button sleep = new Button(group, SWT.PUSH);
		sleep.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sleep.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doSleep();
			}
		});

		Button wake = new Button(group, SWT.PUSH);
		wake.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		wake.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				doWakeUp();
			}
		});

		Button showInDialog = new Button(group, SWT.PUSH);
		showInDialog.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		showInDialog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showInDialog();
			}
		});

	}

	/**
	 * Test the showInDialog API
	 *
	 */
	protected void showInDialog() {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Run in dialog", 100);//$NON-NLS-1$

				for (int i = 0; i < 100; i++) {
					if (monitor.isCanceled())
						return Status.CANCEL_STATUS;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					}
					monitor.worked(1);

				}
				return Status.OK_STATUS;

			}
		};
		showJob.schedule();
		PlatformUI.getWorkbench().getProgressService().showInDialog(
				getSite().getShell(), showJob);

	}

	/**
	 * Wakes up all sleeping test jobs.
	 */
	protected void doWakeUp() {
		Job.getJobManager().wakeUp(TestJob.FAMILY_TEST_JOB);
	}

	/**
	 * Puts to sleep all waiting test jobs.
	 */
	protected void doSleep() {
		Job.getJobManager().sleep(TestJob.FAMILY_TEST_JOB);
	}

	/**
	 * @param body
	 */
	private void createEntryFieldGroup(Composite body) {
		Label label = new Label(body, SWT.NONE);
		durationField = new Combo(body, SWT.DROP_DOWN | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		durationField.setLayoutData(data);
		durationField.select(4);

		label = new Label(body, SWT.NONE);
		delayField = new Text(body, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		delayField.setLayoutData(data);

		label = new Label(body, SWT.NONE);
		quantityField = new Text(body, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		quantityField.setLayoutData(data);

		label = new Label(body, SWT.NONE);
		rescheduleDelay = new Text(body, SWT.BORDER);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH;
		rescheduleDelay.setLayoutData(data);
	}

	/**
	 * Creates all of the checkbox buttons.
	 */
	private void createCheckboxGroup(Composite parent) {
		Composite group = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		group.setLayout(layout);
		group.setLayoutData(new GridData(GridData.FILL_BOTH));

		lockField = new Button(group, SWT.CHECK);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		lockField.setLayoutData(data);

		systemField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		systemField.setLayoutData(data);

		threadField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		threadField.setLayoutData(data);

		groupField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		groupField.setLayoutData(data);

		rescheduleField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		rescheduleField.setLayoutData(data);

		keepField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		keepField.setLayoutData(data);

		keepOneField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		keepOneField.setLayoutData(data);

		unknownField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		unknownField.setLayoutData(data);

		userField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		userField.setLayoutData(data);

		gotoActionField = new Button(group, SWT.CHECK);
		data = new GridData(GridData.FILL_HORIZONTAL);
		gotoActionField.setLayoutData(data);

		schedulingRuleField = new Button(group, SWT.CHECK);
		schedulingRuleField
				.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		failureField = new Button(group, SWT.CHECK);
		failureField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		noPromptField = new Button(group, SWT.CHECK);
		noPromptField
		noPromptField.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	protected void doRun(long duration, IProgressMonitor monitor) {
		final long sleep = 10;
		int ticks = (int) (duration / sleep);
		monitor.beginTask(
				"Spinning inside IProgressService.busyCursorWhile", ticks); //$NON-NLS-1$
		for (int i = 0; i < ticks; i++) {
			if (monitor.isCanceled())
				return;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
			}
			monitor.worked(1);
		}
	}

	protected void doRunInWorkspace(final long duration,
			IProgressMonitor monitor) {
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
				@Override
				public void run(IProgressMonitor monitor) throws CoreException {
					doRun(duration, monitor);
				}
			}, monitor);
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	protected long getDuration() {
		switch (durationField.getSelectionIndex()) {
		case 0:
			return 0;
		case 1:
			return 1;
		case 2:
			return 1000;
		case 3:
			return 10000;
		case 4:
			return 60000;
		case 5:
		default:
			return 600000;
		}
	}

	protected void jobWithRuntimeException() {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				throw new NullPointerException();
			}
		};
		runtimeExceptionJob.schedule();
	}

	/**
	 * Example usage of the IJobManager.join method.
	 */
	protected void joinTestJobs() {
		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InterruptedException {
							Job.getJobManager().join(TestJob.FAMILY_TEST_JOB,
									monitor);
						}
					});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	protected void progressNoFork() {
		try {
			final long duration = getDuration();
			final boolean shouldLock = lockField.getSelection();
			IProgressService progressService = PlatformUI.getWorkbench()
					.getProgressService();
			progressService.runInUI(progressService,
					new IRunnableWithProgress() {
						@Override
						public void run(IProgressMonitor monitor)
								throws InterruptedException {
							if (shouldLock)
								doRunInWorkspace(duration, monitor);
							else
								doRun(duration, monitor);
						}
					}, ResourcesPlugin.getWorkspace().getRoot());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see ViewPart#setFocus()
	 */
	@Override
	public void setFocus() {
		if (durationField != null && !durationField.isDisposed())
			durationField.setFocus();
	}

	protected void touchWorkspace() {
		int jobCount = Integer.parseInt(quantityField.getText());
		for (int i = 0; i < jobCount; i++) {
			getSite().getShell().getDisplay().asyncExec(new Runnable() {
				@Override
				public void run() {
					try {
						ResourcesPlugin.getWorkspace().run(
								new IWorkspaceRunnable() {
									@Override
									public void run(IProgressMonitor monitor) {
									}
								}, null);
					} catch (OperationCanceledException e) {
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	/**
	 * Run a workspace runnable in the application window.
	 *
	 */

	public void runnableInWindow() {

		final long time = getDuration();
		final long sleep = 10;
		IRunnableWithProgress runnableTest = new WorkspaceModifyOperation() {

			@Override
			protected void execute(IProgressMonitor monitor)
					throws CoreException, InvocationTargetException,
					InterruptedException {
				int ticks = (int) (time / sleep);
				monitor.beginTask(
						"Spinning inside ApplicationWindow.run()", ticks); //$NON-NLS-1$
				for (int i = 0; i < ticks; i++) {
					if (monitor.isCanceled())
						return;
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
					}
					monitor.worked(1);
				}
			}

		};
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().run(true,
					true, runnableTest);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
