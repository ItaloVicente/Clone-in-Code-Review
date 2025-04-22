
	private IRepositoryCommit getCommit(IStructuredSelection selection) {
		if (selection != null && selection.size() == 1) {
			Object obj = selection.getFirstElement();
			return AdapterUtils.adapt(obj, IRepositoryCommit.class);
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
