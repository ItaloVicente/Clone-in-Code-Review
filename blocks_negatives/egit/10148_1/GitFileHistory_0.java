		if (!(ifr instanceof CommitFileRevision))
			return NO_REVISIONS;

		final CommitFileRevision rev = (CommitFileRevision) ifr;
		final String p = rev.getGitPath();
		final RevCommit rc = rev.getRevCommit();
		if (!(rc instanceof KidCommit))
			return NO_REVISIONS;
