	public Object getAdapter(Class adapter) {
		if (Repository.class == adapter && myRepository != null)
			return myRepository;
		return super.getAdapter(adapter);
	}

