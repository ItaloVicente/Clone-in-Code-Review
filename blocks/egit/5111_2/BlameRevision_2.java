	public Object getAdapter(Class adapter) {
		if (Repository.class == adapter)
			return repository;
		if (RevCommit.class == adapter)
			return commit;
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
