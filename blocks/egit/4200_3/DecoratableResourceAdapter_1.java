		Set<String> changed = new HashSet<String>(cache.getChanged());
		changed.addAll(cache.getAdded());
		changed.addAll(cache.getRemoved());
		if (containsPrefix(changed, repoRelativePath))
			staged = Staged.MODIFIED;
		else
			staged = Staged.NOT_STAGED;
