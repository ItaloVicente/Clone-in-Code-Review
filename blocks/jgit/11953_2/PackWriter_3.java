		beginPhase(PackingPhase.COMPRESSING
		new DeltaWindow(config
				monitor
				list
		endPhase(monitor);
	}

	private void parallelDeltaSearch(ProgressMonitor monitor
			ObjectToPack[] list
		DeltaCache dc = new ThreadSafeDeltaCache(config);
		ThreadSafeProgressMonitor pm = new ThreadSafeProgressMonitor(monitor);
