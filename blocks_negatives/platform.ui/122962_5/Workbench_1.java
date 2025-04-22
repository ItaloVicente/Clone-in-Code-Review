			String bundleName;
			boolean worked = false;
			synchronized (this) {
				if (eventType == BundleEvent.STARTING) {
					starting.add(bundleName = event.getBundle().getSymbolicName());
				} else if (eventType == BundleEvent.STARTED) {
					progressCount++;
					if (progressCount <= maximumProgressCount) {
						worked = true;
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
			if (worked) {
				progressMonitor.worked(1);
			}
			if (bundleName != null) {
				String taskName = NLS.bind(WorkbenchMessages.Startup_Loading, bundleName);
