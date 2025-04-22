		if (ignored) {
			return IGNORED;
		}
		ResourceState state = new ResourceState();
		Set<String> untrackedFolders = indexDiffData.getUntrackedFolders();
		state.setTracked(
				!containsPrefixPath(untrackedFolders, repoRelativePath));
