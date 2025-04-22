				new ProgressMonitorDialog(window.getShell()).run(true, true, monitor -> {
					monitor.beginTask("Test Job", ITERATIONS);
					int i = 0;
					long result = 0;
					while (i < ITERATIONS) {
						monitor.setTaskName(Integer.toString(i));
						result += i;
						i++;
