	public Object getAdapter(Class adapter) {
		if (adapter == ISharedDocumentAdapter.class) {
			return getSharedDocumentAdapter();
		}
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}
