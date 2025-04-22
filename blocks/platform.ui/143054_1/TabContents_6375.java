					throws Exception {
					section.dispose();
				}
			};
			SafeRunnable.run(runnable);
		}
	}

	public void aboutToBeShown() {
		for (final ISection section : sections) {
			ISafeRunnable runnable = new SafeRunnable() {

				@Override
