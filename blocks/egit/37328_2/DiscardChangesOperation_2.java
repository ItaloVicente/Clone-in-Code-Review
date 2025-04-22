	public void setStage(Stage stage) {
		if (revision != null)
			throw new IllegalStateException(
					"Either stage or revision can be set, but not both"); //$NON-NLS-1$
		this.stage = stage;
	}

