		gcJob = Job.create(IDEWorkbenchMessages.IDEIdleHelper_backgroundGC, (IJobFunction) monitor -> {
			final Display display = configurer.getWorkbench().getDisplay();
			if (display != null && !display.isDisposed()) {
				final long start = System.currentTimeMillis();
				System.gc();
				System.runFinalization();
				lastGC = start;
				final int duration = (int) (System.currentTimeMillis() - start);
				if (Policy.DEBUG_GC) {
					System.out.println("Explicit GC took: " + duration); //$NON-NLS-1$
				}
				if (duration > maxGC) {
