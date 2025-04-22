		checkStateListeners.add(listener);
	}

	public void setCheckStateProvider(ICheckStateProvider checkStateProvider) {
		this.checkStateProvider = checkStateProvider;
		refresh();
	}

	@Override
	protected void doUpdateItem(Item item, E element) {
		super.doUpdateItem(item, element);
		if (!item.isDisposed() && checkStateProvider != null) {
