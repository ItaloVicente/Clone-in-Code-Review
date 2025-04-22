	public Object getAdapter(Class adapter) {
		if (RepositoryCommit.class == adapter)
			return this.commit;

		if (Repository.class == adapter)
			return this.commit.getRepository();

