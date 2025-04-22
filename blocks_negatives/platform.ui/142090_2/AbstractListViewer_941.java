        if (element != null) {
            int ix = getElementIndex(element);
            if (ix >= 0) {
                ILabelProvider labelProvider = (ILabelProvider) getLabelProvider();
                listSetItem(ix, getLabelProviderText(labelProvider,element));
            }
        }
    }

    /**
     * Returns the element with the given index from this list viewer.
     * Returns <code>null</code> if the index is out of range.
     *
     * @param index the zero-based index
     * @return the element at the given index, or <code>null</code> if the
     *   index is out of range
     */
    public Object getElementAt(int index) {
        if (index >= 0 && index < listMap.size()) {
