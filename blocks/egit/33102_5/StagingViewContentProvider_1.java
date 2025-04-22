	private void setSymlinkFileMode(IndexDiffData indexDiff,
			Collection<StagingEntry> entries) {
		final Set<String> symlinks = indexDiff.getSymlinks();
		for (StagingEntry stagingEntry : entries) {
			if (symlinks.contains(stagingEntry.getPath()))
				stagingEntry.setSymlink(true);
		}
	}
