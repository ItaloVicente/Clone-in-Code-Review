	private FetchRecurseSubmodulesMode recurseSubmodules;

	@Option(name = "--recurse-submodules"
	void recurseSubmodules(String mode) {
		recurseSubmodules = (mode == null || mode.isEmpty())
				? FetchRecurseSubmodulesMode.YES
				: FetchRecurseSubmodulesMode.valueOf(mode);
	}

	@Option(name = "--no-recurse-submodules"
	void noRecurseSubmodules(@SuppressWarnings("unused")
	final boolean ignored) {
		recurseSubmodules = FetchRecurseSubmodulesMode.NO;
	}

