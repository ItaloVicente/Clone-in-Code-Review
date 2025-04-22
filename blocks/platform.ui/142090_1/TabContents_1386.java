					throws Exception {
					section.createControls(sectionComposite, page);
				}
			};
			SafeRunnable.run(runnable);
		}
		controlsCreated = true;
	}

	public void dispose() {
		for (final ISection section : sections) {
			ISafeRunnable runnable = new SafeRunnable() {

				@Override
