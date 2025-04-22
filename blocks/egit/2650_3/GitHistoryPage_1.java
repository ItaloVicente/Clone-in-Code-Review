	private void showHead(Repository repo) {
		RevWalk rw = new RevWalk(repo);
		try {
			ObjectId head = repo.resolve(Constants.HEAD);
			RevCommit c = rw.parseCommit(head);
			graph.selectCommitStored(c);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
	}

	private void showRef(Ref ref, Repository repo) {
		RevWalk rw = new RevWalk(repo);
		try {
			RevCommit c = rw.parseCommit(ref.getLeaf().getObjectId());
			graph.selectCommit(c);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
	}

	private void showTag(Ref ref, Repository repo) {
		RevWalk rw = new RevWalk(repo);
		try {
			RevTag t = rw.parseTag(ref.getLeaf().getObjectId());
			RevCommit c = rw.parseCommit(t.getId());
			graph.selectCommit(c);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
	}

