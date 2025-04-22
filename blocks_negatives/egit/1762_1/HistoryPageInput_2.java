
	public Object getAdapter(final Class adapter) {
		if (adapter == IResource.class && list != null && list.length > 0)
			return list[0];
		return null;
	}
