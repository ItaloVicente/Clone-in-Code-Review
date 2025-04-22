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

	private List<IAdaptable> restoreElements(IMemento memento) {
		IMemento[] elementMem = memento.getChildren(TAG_ELEMENT);
		List<IAdaptable> elements = new ArrayList<IAdaptable>(elementMem.length);

		for (IMemento element : elementMem) {
			String factoryID = element.getString(TAG_FACTORY_ID);
			if (factoryID != null) {
				IElementFactory factory = PlatformUI.getWorkbench()
						.getElementFactory(factoryID);
				if (factory != null) {
