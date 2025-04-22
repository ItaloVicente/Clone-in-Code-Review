		}

		return true;
	}

	protected boolean areAllElementsChecked(Object treeElement) {
		List checkedElements = (List) checkedStateStore.get(treeElement);
		if (checkedElements == null) {
