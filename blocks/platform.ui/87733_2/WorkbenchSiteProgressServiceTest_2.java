	class TimeoutReportJob extends Job {
		private long maxTimeoutInMilis;

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
				System.out.println("\nFULL THREAD DUMP START");
				System.out.println(dumpThreads());
				System.out.println("FULL THREAD DUMP END\n");
				File file = Platform.getLogFileLocation().toFile();
				try {
					System.out.println("System log: " + file);
					List<String> lines = Files.readAllLines(file.toPath());
					for (String line : lines) {
						System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return Status.OK_STATUS;
		}
	}

