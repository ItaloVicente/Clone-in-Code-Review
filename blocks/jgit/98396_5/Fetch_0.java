	private FetchRecurseSubmodulesMode recurseSubmodules;

	@Option(name = "--recurse-submodules"
	void recurseSubmodules(String mode) {
		if (mode == null || mode.isEmpty()) {
			recurseSubmodules = FetchRecurseSubmodulesMode.YES;
		} else {
			try {
				recurseSubmodules = FetchRecurseSubmodulesMode.valueOf(mode);
			} catch (IllegalArgumentException e) {
				throw die(MessageFormat.format(
						CLIText.get().invalidRecurseSubmodulesMode
						mode));
			}
		}
	}

	@Option(name = "--no-recurse-submodules"
	void noRecurseSubmodules(@SuppressWarnings("unused")
	final boolean ignored) {
		recurseSubmodules = FetchRecurseSubmodulesMode.NO;
	}

