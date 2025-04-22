	}

	ProgressViewUpdater() {
		createUpdateJob();
		collectors = new IProgressUpdateCollector[0];
	}

	@PostConstruct
	void init(MApplication application) {
		progressManager.addListener(this);
		application.getContext().set(ProgressViewUpdater.class, this);
	}

	void addCollector(IProgressUpdateCollector newCollector) {
		IProgressUpdateCollector[] newCollectors = new IProgressUpdateCollector[collectors.length + 1];
		System.arraycopy(collectors, 0, newCollectors, 0, collectors.length);
		newCollectors[collectors.length] = newCollector;
		collectors = newCollectors;
	}

	void removeCollector(IProgressUpdateCollector provider) {
		HashSet<IProgressUpdateCollector> newCollectors = new HashSet<>();
		for (IProgressUpdateCollector collector : collectors) {
			if (!collector.equals(provider)) {
				newCollectors.add(collector);
			}
		}
		IProgressUpdateCollector[] newArray = new IProgressUpdateCollector[newCollectors.size()];
		newCollectors.toArray(newArray);
		collectors = newArray;
		if (collectors.length == 0) {
			progressManager.removeListener(this);
		}
	}

	void scheduleUpdate() {
		if (PlatformUI.isWorkbenchRunning()) {
