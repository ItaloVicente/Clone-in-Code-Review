	void nameAndStatusOnly(boolean on) {
		if (showNameOnly) {
			throw new IllegalArgumentException(
					CLIText.get().cannotUseNameStatusOnlyAndNameOnly);
		}
		showNameAndStatusOnly = on;
	}

	@Option(name = "--name-only"
	void nameOnly(boolean on) {
		if (showNameAndStatusOnly) {
			throw new IllegalArgumentException(
					CLIText.get().cannotUseNameStatusOnlyAndNameOnly);
		}
		showNameOnly = on;
	}
