	private final class TaskBarDelegatingProgressMonitor implements IProgressMonitor {

		private final Shell shell;
		private IProgressMonitor progessMonitor;
		private TaskItem systemTaskItem;
		private int totalWork;
		private int totalWorked;

		public TaskBarDelegatingProgressMonitor(IProgressMonitor progressMonitor, Shell shell) {
			Assert.isNotNull(progressMonitor);
			this.shell = shell;
			this.progessMonitor = progressMonitor;
		}

		@Override
		public void beginTask(String name, int totalWork) {
			progessMonitor.beginTask(name, totalWork);
			if (this.totalWork == 0) {
				this.totalWork = totalWork;
			}
		}

		@Override
		public void worked(int work) {
			progessMonitor.worked(work);
			totalWorked += work;
			if (Display.getCurrent() != null) {
				handleTaskBarProgressUpdated();
			} else if (getDisplay() != null && !getDisplay().isDisposed()) {
				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						handleTaskBarProgressUpdated();
					}
				});
			}
		}

		@Override
		public void done() {
			progessMonitor.done();

			if (Display.getCurrent() != null) {
				handleTaskBarProgressDone();
			} else if (getDisplay() != null && !getDisplay().isDisposed()) {
				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						handleTaskBarProgressDone();
					}
				});
			}
		}

		private TaskItem getTaskItem(Shell shell) {
			if (Display.getCurrent() == null) {
				return null;
			}
			if (systemTaskItem == null) {
				if (getDisplay() != null && shell != null && !shell.isDisposed()) {
					TaskBar systemTaskBar = getDisplay().getSystemTaskBar();
					if (systemTaskBar != null) {
						systemTaskItem = systemTaskBar.getItem(shell);
						if (systemTaskItem == null) {
							systemTaskItem = systemTaskBar.getItem(null);
						}
					}
				}
			}
			return systemTaskItem;
		}

		private void handleTaskBarProgressUpdated() {
			if (systemTaskItem == null) {
				systemTaskItem = getTaskItem(shell);
			}
			if (systemTaskItem != null && !systemTaskItem.isDisposed()) {
				if (systemTaskItem.getProgressState() != SWT.NORMAL) {
					systemTaskItem.setProgressState(SWT.NORMAL);
				}
				float percentComplete = ((float) totalWorked / (float) totalWork) * 100f;
				systemTaskItem.setProgress(Math.round(percentComplete));
			}
		}

		private void handleTaskBarProgressDone() {
			if (systemTaskItem == null) {
				systemTaskItem = getTaskItem(shell);
			}
			if (systemTaskItem != null && !systemTaskItem.isDisposed()) {
				if (systemTaskItem.getProgressState() != SWT.DEFAULT) {
					systemTaskItem.setProgressState(SWT.DEFAULT);
				}
			}
		}

		@Override
		public void internalWorked(double work) {
			progessMonitor.internalWorked(work);
		}

		@Override
		public boolean isCanceled() {
			return progessMonitor.isCanceled();
		}

		@Override
		public void setCanceled(boolean value) {
			progessMonitor.setCanceled(value);
		}

		@Override
		public void setTaskName(String name) {
			progessMonitor.setTaskName(name);
		}

		@Override
		public void subTask(String name) {
			progessMonitor.subTask(name);
		}

	}

