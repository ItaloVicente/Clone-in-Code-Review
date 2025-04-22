	public Object getAdapter(Class adapter) {
		if (RepositoryCommit.class == adapter)
			return commit;

		if (RevCommit.class == adapter)
			return commit.getRevCommit();

		if (Repository.class == adapter)
			return commit.getRepository();

