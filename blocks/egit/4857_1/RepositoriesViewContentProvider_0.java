
	private synchronized Map<String, Ref> getRefs(final Repository repo, final String prefix) throws IOException {
		Map<String, Ref> allRefs = branchRefs.get(repo);
		if (allRefs == null) {
			allRefs = repo.getRefDatabase().getRefs(RefDatabase.ALL);
			branchRefs.put(repo, allRefs);
			repo.getListenerList().addRefsChangedListener(new RefsChangedListener() {
				public void onRefsChanged(RefsChangedEvent event) {
					synchronized (RepositoriesViewContentProvider.this) {
						branchRefs.remove(repo);
					}
				}
			});
		}
		if (prefix.equals(RefDatabase.ALL))
			return allRefs;
		Map<String, Ref> filtered = new HashMap<String, Ref>();
		for (Map.Entry<String, Ref> entry : allRefs.entrySet()) {
			if (entry.getKey().startsWith(prefix))
				filtered.put(entry.getKey(), entry.getValue());
		}
		return filtered;
	}

