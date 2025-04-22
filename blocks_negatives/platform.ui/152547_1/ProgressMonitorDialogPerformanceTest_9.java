
				for (int testCounter = 0; testCounter < 20; testCounter++) {
					startMeasuring();
					for (int counter = 0; counter < 30; counter++) {
						monitor.setTaskName(taskName);
						processEvents();
					}
					processEvents();
					stopMeasuring();
				}
