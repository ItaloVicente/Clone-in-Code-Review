				new ProgressMonitorDialog(window.getShell()).run(true, true, new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask("Test Job", ITERATIONS);
						int i = 0;
						long result = 0;
						while (i < ITERATIONS) {
							result += i;
							i++;
						}

						endAsyncTest(result);
