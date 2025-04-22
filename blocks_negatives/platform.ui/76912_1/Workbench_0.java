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
