	/**
	 * Return the ProgressMonitorWithBlocking for the receiver.
	 *
	 * @return IProgressMonitorWithBlocking
	 */
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

			/**
			 * Run the runnable as an asyncExec if we are already open.
			 *
			 * @param runnable
			 */
			private void runAsync(final Runnable runnable) {

				if (alreadyClosed) {
					return;
				}
				Shell currentShell = getShell();

				Display display;
				if (currentShell == null) {
					display = Display.getDefault();
				} else {
						return;
					display = currentShell.getDisplay();
				}

				display.asyncExec(new Runnable() {
					@Override
					public void run() {
						if (alreadyClosed) {
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

