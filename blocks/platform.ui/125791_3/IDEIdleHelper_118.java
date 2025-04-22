		gcJob = Job.create(IDEWorkbenchMessages.IDEIdleHelper_backgroundGC, new IJobFunction() {
			@Override
			public IStatus run(IProgressMonitor monitor) {
				final Display display = configurer.getWorkbench().getDisplay();
				if (display != null && !display.isDisposed()) {
					final long start = System.currentTimeMillis();
					System.gc();
					System.runFinalization();
					lastGC = start;
					final int duration = (int) (System.currentTimeMillis() - start);
