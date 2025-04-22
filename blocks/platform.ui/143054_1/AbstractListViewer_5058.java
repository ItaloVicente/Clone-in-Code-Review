	public void insert(Object element, int position) {
		if (getComparator() != null || hasFilters()) {
			add(element);
			return;
		}

		insertItem((ILabelProvider) getLabelProvider(), element, position);
	}


	private String getLabelProviderText(ILabelProvider labelProvider, Object element){
		String text = labelProvider.getText(element);
		if(text == null) {
