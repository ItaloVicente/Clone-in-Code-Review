		childMap.clear();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		childMap.clear();
		application = (MApplication) newInput;
	}

	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}
