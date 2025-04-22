				if (dumpAllThreads) {
					dumpAllThreads = false;
					if (contentionMonitoringSupported) {
						threadMXBean.setThreadContentionMonitoringEnabled(false);
					}
				}
