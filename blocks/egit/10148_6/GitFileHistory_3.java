		if (path != null && commit instanceof KidCommit) {
			final KidCommit c = (KidCommit) commit;
			final IFileRevision[] r = new IFileRevision[c.children.length];
			for (int i = 0; i < r.length; i++)
				r[i] = new CommitFileRevision(db, c.children[i], path);
			return r;
		}
