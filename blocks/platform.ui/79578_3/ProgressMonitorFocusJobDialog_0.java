
		private Display display;

		private Collector collector;

		private String currentTask = ""; //$NON-NLS-1$

		private class Collector implements Runnable {
			private String taskName;

			private String subTask;

			private double worked;

			private IProgressMonitor monitor;

			public Collector(String taskName, String subTask, double work, IProgressMonitor monitor) {
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

		public AccumulatingProgressMonitor(IProgressMonitor monitor, Display display) {
			super(monitor);
			Assert.isNotNull(display);
			this.display = display;
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

		private void createCollector(String taskName, String subTask, double work) {
			collector = new Collector(taskName, subTask, work, getWrappedProgressMonitor());
			display.asyncExec(collector);
		}

		@Override
		public void done() {
			synchronized (this) {
				collector = null;
			}
			display.asyncExec(() -> getWrappedProgressMonitor().done());
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
		public synchronized void worked(int work) {
			internalWorked(work);
		}

		@Override
		public void clearBlocked() {

			final IProgressMonitor pm = getWrappedProgressMonitor();
			if (!(pm instanceof IProgressMonitorWithBlocking)) {
				return;
			}

			display.asyncExec(() -> {
				((IProgressMonitorWithBlocking) pm).clearBlocked();
				Dialog.getBlockedHandler().clearBlocked();
			});
		}

		@Override
		public void setBlocked(final IStatus reason) {
			final IProgressMonitor pm = getWrappedProgressMonitor();
			if (!(pm instanceof IProgressMonitorWithBlocking)) {
				return;
			}

			display.asyncExec(() -> {
				((IProgressMonitorWithBlocking) pm).setBlocked(reason);
				Dialog.getBlockedHandler().showBlocked(pm, reason, currentTask);
			});
		}
	}

