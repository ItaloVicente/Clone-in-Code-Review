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
			RevCommit c = null;
			RevObject any = rw.parseAny(ref.getLeaf().getObjectId());
			if (any instanceof RevCommit) {
				c = (RevCommit) any;
			} else if (any instanceof RevTag) {
				RevTag t = rw.parseTag(any);
				c = rw.parseCommit(t.getObject());
			}
			if (c != null)
				graph.selectCommit(c);
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
		}
	}

