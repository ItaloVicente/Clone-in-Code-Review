		Set<String> changed = new HashSet<String>(indexDiffData.getChanged());
		changed.addAll(indexDiffData.getAdded());
		changed.addAll(indexDiffData.getRemoved());
		if (containsPrefix(changed, repoRelativePath))
			staged = Staged.MODIFIED;
		else
			staged = Staged.NOT_STAGED;
