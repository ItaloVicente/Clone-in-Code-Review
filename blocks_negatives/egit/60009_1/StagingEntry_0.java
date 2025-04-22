	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IResource.class)
			return getFile();
		else if (adapter == IPath.class)
			return getLocation();
		return null;
	}

