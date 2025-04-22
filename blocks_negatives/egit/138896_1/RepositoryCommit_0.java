	public Object getAdapter(Class adapter) {
		if (Repository.class == adapter)
			return repository;

		if (RevCommit.class == adapter)
			return commit;

