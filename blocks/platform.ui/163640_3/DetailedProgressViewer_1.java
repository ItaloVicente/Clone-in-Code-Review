		int jobsCount = (int) jobItemControls.keySet().stream().filter(x -> x.isActive()).count();
		listeners.stream().forEach(x -> x.handle(jobsCount));
	}

	void addProgressListener(IProgressViewerListener listener) {
		listeners.add(listener);
	}

	void removeProgressListener(IProgressViewerListener listener) {
		listeners.remove(listener);
