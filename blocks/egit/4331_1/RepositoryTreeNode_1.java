	public Object getAdapter(Class adapter) {
		if (Repository.class == adapter)
			return myRepository;
		return super.getAdapter(adapter);
	}

