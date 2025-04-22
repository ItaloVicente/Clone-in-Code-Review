
	private void setSubmoduleFileMode(IndexDiffData indexDiff,
			Collection<StagingEntry> entries) {
		final Set<String> submodules = indexDiff.getSubmodules();
		for (StagingEntry stagingEntry : entries) {
			if (submodules.contains(stagingEntry.getPath()))
				stagingEntry.setSubmodule(true);
		}
	}
