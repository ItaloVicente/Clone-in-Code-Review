	@Override
	public Object getAdapter(Class adapter) {
		Object result = super.getAdapter(adapter);
		if (result != null && adapter.isInstance(result)) {
			return result;
		}
		if (adapter == Repository.class) {
			return AdapterUtils.adapt(fileRevision, Repository.class);
		}
		return null;
	}

