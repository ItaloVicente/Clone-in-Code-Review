		checkStateListeners.add(listener);
	}

	public void setCheckStateProvider(ICheckStateProvider checkStateProvider) {
		this.checkStateProvider = checkStateProvider;
		refresh();
	}

	@Override
	protected void doUpdateItem(Widget widget, E element, boolean fullMap) {
		super.doUpdateItem(widget, element, fullMap);
		if (!widget.isDisposed()) {
			if (checkStateProvider != null) {
