		Git git = new Git(db);
		for (int i = 0; i < count; i++)
			git.commit().setCommitter(committer).setAuthor(author)
					.setMessage("commit " + i).call();
		list = new RevCommitList<RevCommit>();
		RevWalk w = new RevWalk(db);
		w.markStart(w.lookupCommit(db.resolve(Constants.HEAD)));
		list.source(w);
