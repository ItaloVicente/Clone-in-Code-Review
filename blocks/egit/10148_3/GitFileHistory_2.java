		final IFileRevision[] allRevisions = getFileRevisions();
		final List<IFileRevision> descendants = new ArrayList<IFileRevision>();
		if (!(ifr instanceof CommitFileRevision)) {
			for (IFileRevision candidate : allRevisions) {
				if (candidate.getTimestamp() < ifr.getTimestamp())
					descendants.add(candidate);
			}
		} else {
			final CommitFileRevision rev = (CommitFileRevision) ifr;
			final RevCommit rc = rev.getRevCommit();

			for (IFileRevision candidate : allRevisions) {
				if (candidate instanceof CommitFileRevision) {
					final RevCommit candidateCommit = ((CommitFileRevision) candidate)
							.getRevCommit();
					if (candidateCommit.getCommitTime() < rc.getCommitTime())
						descendants.add(candidate);
				}
			}
		}
		return descendants.toArray(new IFileRevision[descendants.size()]);
