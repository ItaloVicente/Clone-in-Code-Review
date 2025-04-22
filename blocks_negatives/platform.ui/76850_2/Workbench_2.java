			String bundleName;

			synchronized (this) {
				if (eventType == BundleEvent.STARTING) {
					starting.add(bundleName = event.getBundle().getSymbolicName());
				} else if (eventType == BundleEvent.STARTED) {
					progressCount++;
					if (progressCount <= maximumProgressCount) {
						progressMonitor.worked(1);
					}
					int index = starting.lastIndexOf(event.getBundle().getSymbolicName());
					if (index >= 0) {
						starting.remove(index);
					}
					if (index != starting.size()) {
					}
					bundleName = index == 0 ? null : (String) starting.get(index - 1);
				} else {
				}
			}

			if (bundleName != null) {
				String taskName = NLS.bind(WorkbenchMessages.Startup_Loading, bundleName);
				progressMonitor.subTask(taskName);
