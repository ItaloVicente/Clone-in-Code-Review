	public RevCommit getHeadCommit(Repository repository) {
		if (repository == null) {
			return null;
		}
		Map<RepositoryItem, Object> items = getItems(repository);
		Object value = items.get(RepositoryItem.HEAD_COMMIT);
		if (value == null) {
			ObjectId headId = getHead(repository);
			if (headId != null) {
				try (RevWalk w = new RevWalk(repository)) {
					RevCommit commit = w.parseCommit(headId);
					items.put(RepositoryItem.HEAD_COMMIT, commit);
					return commit;
				} catch (IOException e) {
				}
			}
			items.put(RepositoryItem.HEAD_COMMIT, NOTHING);
			return null;
		} else if (value == NOTHING) {
			return null;
		}
		return (RevCommit) value;
	}

