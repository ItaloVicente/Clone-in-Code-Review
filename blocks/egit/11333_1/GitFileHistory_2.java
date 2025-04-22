		if (path != null && commit != null) {
			final IFileRevision[] r = new IFileRevision[commit.getParentCount()];
			for (int i = 0; i < r.length; i++)
				r[i] = new CommitFileRevision(db, commit.getParent(i), path);
			return r;
		}

		return NO_REVISIONS;
