		final CommitFileRevision rev = (CommitFileRevision) ifr;
		final String p = rev.getGitPath();
		final RevCommit c = rev.getRevCommit();
		final IFileRevision[] r = new IFileRevision[c.getParentCount()];
		for (int i = 0; i < r.length; i++)
			r[i] = new CommitFileRevision(db, c.getParent(i), p);
		return r;
