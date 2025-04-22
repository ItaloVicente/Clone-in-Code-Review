		Object adapter = input.getAdapter(IFile.class);
		if (adapter != null) {
			return (IResource) adapter;
		}
		adapter = input.getAdapter(IResource.class);
		if (adapter != null) {
			return (IResource) adapter;
		}
		adapter = Platform.getAdapterManager().getAdapter(input, IFile.class);
		if (adapter != null) {
			return (IResource) adapter;
		}
		adapter = Platform.getAdapterManager().getAdapter(input, IResource.class);
