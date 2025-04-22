	public Object getAdapter(Class adapter) {
		if ((adapter == IFile.class || adapter == IResource.class)
				&& isUIThread()) {
			Object selectedEdition = getSelectedEdition();
			if (selectedEdition instanceof DiffNode) {
				DiffNode diffNode = (DiffNode) selectedEdition;
				ITypedElement element = diffNode.getLeft();
				IResource resource = null;
				if (element instanceof HiddenResourceTypedElement) {
					resource = ((HiddenResourceTypedElement) element)
							.getRealFile();
				}
				if (resource == null && element instanceof IResourceProvider) {
					resource = ((IResourceProvider) element).getResource();
				}
				if (resource != null && adapter.isInstance(resource)) {
					return resource;
				}
			}
		}
		return super.getAdapter(adapter);
	}

	@Override
	protected void contentsCreated() {
		super.contentsCreated();
		getNavigator().selectChange(true);
