		if (!(ifr instanceof CommitFileRevision))
			return NO_REVISIONS;

		final CommitFileRevision rev = (CommitFileRevision) ifr;
		final String p = rev.getGitPath();
		final RevCommit rc = rev.getRevCommit();
		if (!(rc instanceof KidCommit))
			return NO_REVISIONS;

		final KidCommit c = (KidCommit) rc;
		final IFileRevision[] r = new IFileRevision[c.children.length];
		for (int i = 0; i < r.length; i++)
			r[i] = new CommitFileRevision(db, c.children[i], p);
		return r;
