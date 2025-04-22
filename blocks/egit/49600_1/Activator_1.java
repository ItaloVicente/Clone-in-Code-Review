			if (!monitor.isCanceled()) {
				synchronized (repositoriesChanged) {
					if (!repositoriesChanged.isEmpty()) {
						schedule(100);
					}
				}
			}
