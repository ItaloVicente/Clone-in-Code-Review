		if (selection.isEmpty()) {
			return null;
		}
		Set<Repository> set = getRepositoriesFromSelection(selection);
		if (set.size() == 1) {
			return set.iterator().next();
		}
