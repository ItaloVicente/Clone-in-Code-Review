	private synchronized Map<String, Ref> getRefs(final Repository repo, final String prefix) throws IOException {
		Map<String, Ref> allRefs = branchRefs.get(repo);
		if (allRefs == null) {
			allRefs = repo.getRefDatabase().getRefs(RefDatabase.ALL);
			branchRefs.put(repo, allRefs);
			if (refsChangedListeners.get(repo) == null) {
				refsChangedListeners.put(repo, repo.getListenerList()
						.addRefsChangedListener(event -> {
							synchronized (this) {
								branchRefs.remove(event.getRepository());
							}
						}));
			}
		}
		if (prefix.equals(RefDatabase.ALL)) {
			return allRefs;
		}
		Map<String, Ref> filtered = new HashMap<>();
		for (Map.Entry<String, Ref> entry : allRefs.entrySet()) {
			if (entry.getKey().startsWith(prefix)) {
				filtered.put(entry.getKey(), entry.getValue());
			}
		}
		return filtered;
