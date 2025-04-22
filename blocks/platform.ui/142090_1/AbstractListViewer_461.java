	private java.util.List listMap = new ArrayList();

	protected abstract void listAdd(String string, int index);

	protected abstract void listSetItem(int index, String string);

	protected abstract int[] listGetSelectionIndices();

	protected abstract int listGetItemCount();

	protected abstract void listSetItems(String[] labels);

	protected abstract void listRemoveAll();

	protected abstract void listRemove(int index);

	protected abstract void listSetSelection(int[] ixs);

	protected abstract void listShowSelection();

	protected abstract void listDeselectAll();

	public void add(Object[] elements) {
		assertElementsNotNull(elements);
		Object[] filtered = filter(elements);
		ILabelProvider labelProvider = (ILabelProvider) getLabelProvider();
		for (Object element : filtered) {
			int ix = indexForElement(element);
			insertItem(labelProvider, element, ix);
		}
	}

	private void insertItem(ILabelProvider labelProvider, Object element, int index) {
		listAdd(getLabelProviderText(labelProvider, element), index);
