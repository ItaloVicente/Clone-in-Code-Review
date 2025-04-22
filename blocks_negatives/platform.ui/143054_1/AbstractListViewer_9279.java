    public void insert(Object element, int position) {
    	if (getComparator() != null || hasFilters()) {
    		add(element);
    		return;
    	}

    	insertItem((ILabelProvider) getLabelProvider(), element, position);
    }


    /**
     * Return the text for the element from the labelProvider.
     * If it is null then return the empty String.
     * @param labelProvider ILabelProvider
     * @param element
     * @return String. Return the emptyString if the labelProvider
     * returns null for the text.
     *
     * @since 3.1
     */
    private String getLabelProviderText(ILabelProvider labelProvider, Object element){
    	String text = labelProvider.getText(element);
        if(text == null) {
