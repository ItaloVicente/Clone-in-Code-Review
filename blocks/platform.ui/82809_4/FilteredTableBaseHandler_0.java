	private PerspectiveLabelProvider perspectiveLabelProvider = null;

	private PerspectiveLabelProvider getPerspectiveLabelProvider() {
		if (perspectiveLabelProvider == null) {
			perspectiveLabelProvider = new PerspectiveLabelProvider(false);
		}
		return perspectiveLabelProvider;
	}

