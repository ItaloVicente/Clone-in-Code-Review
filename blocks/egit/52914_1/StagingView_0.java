	@Override
	public void saveState(IMemento memento) {
		super.saveState(memento);
		memento.putBoolean(STORE_SORT_STATE, sortAction.isChecked());
	}

