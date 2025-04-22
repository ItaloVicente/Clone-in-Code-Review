		IProgressUpdateCollector[] newArray = new IProgressUpdateCollector[newCollectors.size()];
		newCollectors.toArray(newArray);
		collectors = newArray;
		if (collectors.length == 0) {
			progressManager.removeListener(this);
		}
	}

	void scheduleUpdate() {
		if (PlatformUI.isWorkbenchRunning()) {
