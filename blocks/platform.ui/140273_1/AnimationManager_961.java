				return Status.OK_STATUS;
			}
		};
		animationUpdateJob.setSystem(true);

		listener = getProgressListener();
		ProgressManager.getInstance().addListener(listener);

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

