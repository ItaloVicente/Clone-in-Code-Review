					throws Exception {
					section.setInput(part, selection);
				}
			};
			SafeRunnable.run(runnable);
		}
	}

	public void setSections(ISection[] sections) {
		this.sections = sections;
	}

	public boolean controlsHaveBeenCreated() {
		return controlsCreated;
	}

	public void refresh() {
		if (controlsCreated) {
			for (final ISection section : sections) {
				ISafeRunnable runnable = new SafeRunnable() {

					@Override
