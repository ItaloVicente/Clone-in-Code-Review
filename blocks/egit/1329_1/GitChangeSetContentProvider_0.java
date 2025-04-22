	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof GitModelBlob)
			return false;

		return super.hasChildren(element);
	}

