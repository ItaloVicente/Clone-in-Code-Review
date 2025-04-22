		synchronized (runnableMonitors) {
			JobMonitor monitor = runnableMonitors.get(job);
			if (monitor == null) {
				monitor = new JobMonitor(job);
				runnableMonitors.put(job, monitor);
			}

			return monitor;
		}
