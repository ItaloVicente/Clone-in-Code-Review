	private FetchRecurseSubmodulesMode recurseSubmodules;

	@Option(name = "--recurse-submodules"
	void recurseSubmodules(String mode) {
		if (mode == null || mode.isEmpty()) {
			recurseSubmodules = FetchRecurseSubmodulesMode.YES;
		} else {
			for (FetchRecurseSubmodulesMode m : FetchRecurseSubmodulesMode
					.values()) {
				if (m.matchConfigValue(mode)) {
					recurseSubmodules = m;
					return;
				}
			}
			throw die(MessageFormat
					.format(CLIText.get().invalidRecurseSubmodulesMode
		}
	}

	@Option(name = "--no-recurse-submodules"
	void noRecurseSubmodules(@SuppressWarnings("unused")
	final boolean ignored) {
		recurseSubmodules = FetchRecurseSubmodulesMode.NO;
	}

