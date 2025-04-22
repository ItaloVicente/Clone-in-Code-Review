		RevCommit commit = null;
		try {
			RevWalk w = new RevWalk(repo);
			commit = w.parseCommit(entry.getNewId());
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
