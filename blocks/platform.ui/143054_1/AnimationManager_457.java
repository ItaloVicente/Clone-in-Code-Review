				return Status.OK_STATUS;
			}
		};
		animationUpdateJob.setSystem(true);

		listener = getProgressListener();
		progressManager.addListener(listener);


	}

	void addItem(final AnimationItem item) {
		animationProcessor.addItem(item);
	}

	void removeItem(final AnimationItem item) {
		animationProcessor.removeItem(item);
	}

	boolean isAnimated() {
		return animated;
	}

	void setAnimated(final boolean bool) {
		animated = bool;
		animationUpdateJob.schedule(100);
	}

	@PreDestroy
	void dispose() {
		setAnimated(false);
		progressManager.removeListener(listener);
	}

	private IJobProgressManagerListener getProgressListener() {
		return new IJobProgressManagerListener() {
			Set<Job> jobs = Collections.synchronizedSet(new HashSet<Job>());

			@Override
