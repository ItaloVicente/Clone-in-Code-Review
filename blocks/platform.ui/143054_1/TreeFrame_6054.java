		setName(name);
		setToolTipText(name);
	}

	public Object[] getExpandedElements() {
		return expandedElements;
	}

	public Object getInput() {
		return input;
	}

	public ISelection getSelection() {
		return selection;
	}

	public AbstractTreeViewer getViewer() {
		return viewer;
	}

	private List restoreElements(IMemento memento) {
		IMemento[] elementMem = memento.getChildren(TAG_ELEMENT);
		List elements = new ArrayList(elementMem.length);

		for (IMemento currentMemento : elementMem) {
			String factoryID = currentMemento.getString(TAG_FACTORY_ID);
			if (factoryID != null) {
				IElementFactory factory = PlatformUI.getWorkbench()
						.getElementFactory(factoryID);
				if (factory != null) {
