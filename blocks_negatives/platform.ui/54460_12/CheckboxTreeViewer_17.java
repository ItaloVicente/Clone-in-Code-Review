        checkStateListeners.add(listener);
    }

    /**
     * Sets the {@link ICheckStateProvider} for this {@link CheckboxTreeViewer}.
     * The check state provider will supply the logic for deciding whether the
     * check box associated with each item should be checked, grayed or
     * unchecked.
     * @param checkStateProvider	The provider.
     * @since 3.5
     */
    public void setCheckStateProvider(ICheckStateProvider checkStateProvider) {
    	this.checkStateProvider = checkStateProvider;
    	refresh();
    }

    /*
     * Extends this method to update check box states.
     */
    @Override
	protected void doUpdateItem(Item item, Object element) {
    	super.doUpdateItem(item, element);
    	if(!item.isDisposed() && checkStateProvider != null) {
