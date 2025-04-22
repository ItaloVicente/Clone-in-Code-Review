					throws Exception {
					section.aboutToBeShown();
				}
			};
			SafeRunnable.run(runnable);
		}
	}

	public void aboutToBeHidden() {
		for (final ISection section : sections) {
			ISafeRunnable runnable = new SafeRunnable() {

				@Override
