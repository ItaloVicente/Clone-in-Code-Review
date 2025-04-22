	@Override
	public boolean isEnabled() {
		IStructuredSelection selection = getSelection();
		if (selection.size() != 1) {
			return false;
		}
		Object element = selection.getFirstElement();
		IResource resource = AdapterUtils.adapt(element, IResource.class);
		if (resource instanceof IStorage) {
			return RepositoryMapping.getMapping(resource) != null;
