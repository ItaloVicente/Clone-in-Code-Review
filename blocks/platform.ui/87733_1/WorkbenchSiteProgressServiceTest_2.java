	class TimeoutReportJob extends Job {
		private long maxTimeoutInMilis;
		int runs = 1;

		public TimeoutReportJob(String name, long maxTimeoutInMilis) {
			super(name);
			this.maxTimeoutInMilis = maxTimeoutInMilis;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			while (!teardownCalled && System.currentTimeMillis() - startTime < maxTimeoutInMilis) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					break;
				}
			}
			if (!teardownCalled) {
				System.out.println("Test seem to hang: " + getName());
				System.out.println("\nFULL THREAD DUMP #" + runs + " START");
				System.out.println(dumpThreads());
				System.out.println("FULL THREAD DUMP #" + runs + " END\n");
				if (runs <= 3) {
					runs++;
					PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
						@Override
						public void run() {
							System.out.println("UI thread seem to work, starting the job again");
							schedule(5000);
						}
					});
				}
			}
			return Status.OK_STATUS;
		}
	}

