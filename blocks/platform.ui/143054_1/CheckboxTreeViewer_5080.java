		checkStateListeners.add(listener);
	}

	public void setCheckStateProvider(ICheckStateProvider checkStateProvider) {
		this.checkStateProvider = checkStateProvider;
		refresh();
	}

	@Override
