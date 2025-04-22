				fireChange = false;
			}
			synchronized (updaters) {
				IWorkingSetUpdater old = updaters.putIfAbsent(updaterId, updater);
				if (fireChange) {
					fireChange = old == null;
				}
			}
			if (fireChange) {
