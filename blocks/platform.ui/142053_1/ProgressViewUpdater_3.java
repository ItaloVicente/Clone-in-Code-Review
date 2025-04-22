				if (!treeElement.isActive()) {
					obsoleteRefresh.add(treeElement);
					deletions.add(treeElement);
				}
			}

			refreshes.removeAll(obsoleteRefresh);

		}
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
		for (int i = 0; i < collectors.length; i++) {
			if (!collectors[i].equals(provider)) {
