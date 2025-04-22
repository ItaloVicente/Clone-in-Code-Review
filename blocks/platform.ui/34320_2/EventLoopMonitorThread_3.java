				if (!dumpAllThreads && currTime >= lastEventStartOrResumeTime + allThreadsSampleInterval) {
					dumpAllThreads = true;
					if (contentionMonitoringSupported) {
						threadMXBean.setThreadContentionMonitoringEnabled(true);
					}
				}

