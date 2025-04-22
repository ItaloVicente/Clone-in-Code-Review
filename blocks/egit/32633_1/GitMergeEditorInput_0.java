	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == IFile.class || adapter == IResource.class) {
			Object selectedEdition = getSelectedEdition();
			if (selectedEdition instanceof DiffNode) {
				DiffNode diffNode = (DiffNode) selectedEdition;
				ITypedElement element = diffNode.getLeft();
				if (element instanceof ResourceEditableRevision) {
					ResourceEditableRevision resourceRevision = (ResourceEditableRevision) element;
					return resourceRevision.getFile();
				}
			}
		}
		return super.getAdapter(adapter);
	}

