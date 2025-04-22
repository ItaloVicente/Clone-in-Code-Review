	private final Map<Repository, String> submodules = new HashMap<>();

	private final IndexDiffChangedListener submoduleListener = (submodule,
			diffData) -> {
		String path = submodules.get(submodule);
		if (path != null) {
			scheduleUpdateJob(Collections.singletonList(path),
					Collections.emptyList());
		}
	};
