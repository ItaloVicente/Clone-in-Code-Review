		if (element != null) {
			int ix = getElementIndex(element);
			if (ix >= 0) {
				ILabelProvider labelProvider = (ILabelProvider) getLabelProvider();
				listSetItem(ix, getLabelProviderText(labelProvider,element));
			}
		}
	}

	public Object getElementAt(int index) {
		if (index >= 0 && index < listMap.size()) {
