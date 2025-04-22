		try (Git git = new Git(db);
				RevWalk w = new RevWalk(db);) {
			for (int i = 0; i < count; i++)
				git.commit().setCommitter(committer).setAuthor(author)
						.setMessage("commit " + i).call();
			list = new RevCommitList<RevCommit>();
			w.markStart(w.lookupCommit(db.resolve(Constants.HEAD)));
			list.source(w);
		}
