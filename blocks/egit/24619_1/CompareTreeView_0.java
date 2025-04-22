	static class SamplingMonitor {
		String currentFile;

		private Thread thread;

		private IProgressMonitor monitor;

		private volatile String taskName;

		SamplingMonitor(IProgressMonitor monitor) {
			this.monitor = monitor;

		}
		void start() {
			thread = new Thread() {
				public void run() {
					for (;;) {
						try {
							Thread.sleep(5);
							monitor.setTaskName(taskName);
						} catch (InterruptedException e) {
							break;
						}
						if (monitor.isCanceled())
							break;
					}
					System.out.println("DONNNE"); //$NON-NLS-1$
				}
			};
		}

		void stop() {
			thread.interrupt();
		}

		void setTaskName(String name) {
			taskName = name;
		}
	}
