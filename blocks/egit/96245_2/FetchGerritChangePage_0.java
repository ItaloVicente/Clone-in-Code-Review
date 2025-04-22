package org.eclipse.egit.ui.internal.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ProgressMonitorWrapper;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class NonBlockingWizardDialog extends WizardDialog {

	private Queue<BackgroundJob> jobs = new LinkedList<>();

	public NonBlockingWizardDialog(Shell parentShell, IWizard newWizard) {
		super(parentShell, newWizard);
	}

	@Override
	protected Control createContents(Composite parent) {
		parent.addDisposeListener(e -> cancelJobs());
		addPageChangedListener(e -> cancelJobs());
		return super.createContents(parent);
	}

	@Override
	public void run(boolean fork, boolean cancelable,
			IRunnableWithProgress runnable)
			throws InvocationTargetException, InterruptedException {
		if (!fork) {
			super.run(fork, cancelable, runnable);
		}
		run(runnable, null);
	}

	public void run(IRunnableWithProgress runnable, Runnable onCancel) {
		Assert.isNotNull(runnable);
		synchronized (jobs) {
			BackgroundJob newJob = new BackgroundJob(runnable, onCancel,
					getCurrentPage());
			jobs.add(newJob);
			if (jobs.size() == 1) {
				newJob.schedule();
			}
		}
	}

	public void cancelJobs() {
		Job currentJob;
		synchronized (jobs) {
			currentJob = jobs.peek();
			jobs.clear();
		}
		if (currentJob != null) {
			currentJob.cancel();
		}
	}

	@Override
	public void showPage(IWizardPage page) {
		synchronized (jobs) {
			super.showPage(page);
		}
	}

	@Override
	public IWizardPage getCurrentPage() {
		synchronized (jobs) {
			return super.getCurrentPage();
		}
	}

	@Override
	protected final ProgressMonitorPart createProgressMonitorPart(
			Composite composite, GridLayout pmlayout) {
		return new BackgroundProgressMonitorPart(composite, pmlayout);
	}

	private void restoreFocus(Control focusControl) {
		if (focusControl != null && !focusControl.isDisposed()) {
			Shell shell = getShell();
			if (shell != null && !shell.isDisposed()
					&& focusControl.getShell() == shell) {
				focusControl.setFocus();
			}
		}
	}

	private class BackgroundProgressMonitorPart extends ProgressMonitorPart {

		private Job job;

		public BackgroundProgressMonitorPart(Composite parent, Layout layout) {
			super(parent, layout, true);
		}

		public void setJob(Job job) {
			this.job = job;
		}

		@Override
		public void beginTask(String name, int totalWork) {
			Display display = this.getDisplay();
			Control focusControl = display.isDisposed() ? null
					: display.getFocusControl();
			super.beginTask(name, totalWork);
			restoreFocus(focusControl);
		}

		@Override
		public void setCanceled(boolean cancel) {
			super.setCanceled(cancel);
			if (cancel) {
				if (job != null) {
					job.cancel();
				}
			}
		}

		@Override
		public void done() {
			job = null;
			super.done();
		}
	}

	private static class ForwardingProgressMonitor
			extends ProgressMonitorWrapper {
		private Display display;

		private Collector collector;

		private IProgressMonitor jobMonitor;

		private String currentTask = ""; //$NON-NLS-1$

		private class Collector implements Runnable {
			private String taskName;

			private String subTask;

			private double worked;

			private IProgressMonitor monitor;

			public Collector(String taskName, String subTask, double work,
					IProgressMonitor monitor) {
				this.taskName = taskName;
				this.subTask = subTask;
				this.worked = work;
				this.monitor = monitor;
			}

			public void setTaskName(String name) {
				this.taskName = name;
			}

			public void worked(double workedIncrement) {
				this.worked = this.worked + workedIncrement;
			}

			public void subTask(String subTaskName) {
				this.subTask = subTaskName;
			}

			@Override
			public void run() {
				clearCollector(this);
				if (taskName != null) {
					monitor.setTaskName(taskName);
				}
				if (subTask != null) {
					monitor.subTask(subTask);
				}
				if (worked > 0) {
					monitor.internalWorked(worked);
				}
			}
		}

		public ForwardingProgressMonitor(IProgressMonitor monitor,
				IProgressMonitor jobMonitor, Display display) {
			super(monitor);
			Assert.isNotNull(display);
			this.display = display;
			this.jobMonitor = jobMonitor;
		}

		@Override
		public boolean isCanceled() {
			return jobMonitor.isCanceled() || super.isCanceled();
		}

		@Override
		public void beginTask(final String name, final int totalWork) {
			synchronized (this) {
				collector = null;
			}
			display.asyncExec(() -> {
				currentTask = name;
				getWrappedProgressMonitor().beginTask(name, totalWork);
			});
		}

		private synchronized void clearCollector(Collector collectorToClear) {
			if (this.collector == collectorToClear) {
				this.collector = null;
			}
		}

		private void createCollector(String taskName, String subTask,
				double work) {
			collector = new Collector(taskName, subTask, work,
					getWrappedProgressMonitor());
			display.asyncExec(collector);
		}

		@Override
		public void done() {
			synchronized (this) {
				collector = null;
			}
			if (!display.isDisposed()) {
				display.asyncExec(() -> {
					try {
						getWrappedProgressMonitor().done();
					} catch (SWTException e) {
					}
				});
			}
		}

		@Override
		public synchronized void internalWorked(final double work) {
			if (collector == null) {
				createCollector(null, null, work);
			} else {
				collector.worked(work);
			}
		}

		@Override
		public synchronized void setTaskName(final String name) {
			currentTask = name;
			if (collector == null) {
				createCollector(name, null, 0);
			} else {
				collector.setTaskName(name);
			}
		}

		@Override
		public synchronized void subTask(final String name) {
			if (collector == null) {
				createCollector(null, name, 0);
			} else {
				collector.subTask(name);
			}
		}

		@Override
		public void worked(int work) {
			internalWorked(work);
		}

		@Override
		public void clearBlocked() {
			IProgressMonitor wrapped = getWrappedProgressMonitor();
			if (!(wrapped instanceof IProgressMonitorWithBlocking)) {
				return;
			}

			display.asyncExec(() -> {
				((IProgressMonitorWithBlocking) wrapped).clearBlocked();
				Dialog.getBlockedHandler().clearBlocked();
			});
		}

		@Override
		public void setBlocked(final IStatus reason) {
			IProgressMonitor wrapped = getWrappedProgressMonitor();
			if (!(wrapped instanceof IProgressMonitorWithBlocking)) {
				return;
			}

			display.asyncExec(() -> {
				((IProgressMonitorWithBlocking) wrapped).setBlocked(reason);
				Dialog.getBlockedHandler().showBlocked(wrapped, reason,
						currentTask);
			});
		}

	}

	private class BackgroundJob extends Job {

		private IRunnableWithProgress runnable;

		private Runnable onCancel;

		private IWizardPage page;

		public BackgroundJob(IRunnableWithProgress runnable, Runnable onCancel,
				IWizardPage page) {
			super(MessageFormat.format(
					UIText.NonBlockingWizardDialog_BackgroundJobName,
					page.getName()));
			this.runnable = runnable;
			this.onCancel = onCancel;
			this.page = page;
			this.addJobChangeListener(new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					if (!PlatformUI.isWorkbenchRunning()) {
						return;
					}
					Display display = PlatformUI.getWorkbench().getDisplay();
					if (display == null || display.isDisposed()) {
						return;
					}
					display.syncExec(() -> {
						boolean hideProgress = false;
						synchronized (jobs) {
							Job currentJob = jobs.peek();
							if (currentJob == BackgroundJob.this) {
								jobs.poll();
								Job nextJob = jobs.peek();
								if (nextJob != null) {
									nextJob.schedule();
								} else {
									hideProgress = true;
								}
							} else if (currentJob == null) {
								hideProgress = true;
							}
							if (hideProgress) {
								IProgressMonitor uiMonitor = getProgressMonitor();
								if (uiMonitor instanceof ProgressMonitorPart) {
									ProgressMonitorPart part = ((ProgressMonitorPart) uiMonitor);
									if (!part.isDisposed()) {
										part.setVisible(false);
										part.removeFromCancelComponent(null);
									}
								}
							}
						}
					});
				}
			});
			this.setUser(false);
			this.setSystem(true);
		}

		@Override
		public boolean shouldRun() {
			synchronized (jobs) {
				return page == getCurrentPage() && jobs.peek() == this;
			}
		}

		@Override
		protected void canceling() {
			try {
				if (onCancel != null) {
					onCancel.run();
				}
			} finally {
				super.canceling();
			}
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			if (!shouldRun()) {
				return Status.CANCEL_STATUS;
			}
			IProgressMonitor uiMonitor = getProgressMonitor();
			IProgressMonitor combinedMonitor;
			if (uiMonitor instanceof ProgressMonitorPart) {
				ProgressMonitorPart part = ((ProgressMonitorPart) uiMonitor);
				IProgressMonitor[] newMonitor = { null };
				Display display = PlatformUI.getWorkbench().getDisplay();
				if (display == null || display.isDisposed()) {
					return Status.CANCEL_STATUS;
				}
				display.syncExec(() -> {
					if (((ProgressMonitorPart) uiMonitor).isDisposed()) {
						return;
					}
					try {
						Control focusControl = display.getFocusControl();
						part.setVisible(true);
						part.attachToCancelComponent(null);
						restoreFocus(focusControl);
						if (part instanceof BackgroundProgressMonitorPart) {
							((BackgroundProgressMonitorPart) part)
									.setJob(BackgroundJob.this);
						}
						newMonitor[0] = new ForwardingProgressMonitor(uiMonitor,
								monitor, part.getDisplay());
					} catch (SWTException e) {
						return;
					}
				});
				combinedMonitor = newMonitor[0];
				if (combinedMonitor == null) {
					return Status.CANCEL_STATUS;
				}
			} else {
				combinedMonitor = monitor;
			}
			try {
				runnable.run(combinedMonitor);
			} catch (InvocationTargetException e) {
				return Activator.createErrorStatus(e.getLocalizedMessage(), e);
			} catch (InterruptedException e) {
				return Status.CANCEL_STATUS;
			} finally {
				monitor.done();
				if (combinedMonitor != monitor) {
					combinedMonitor.done();
				}
			}
			return Status.OK_STATUS;
		}

	}
}
