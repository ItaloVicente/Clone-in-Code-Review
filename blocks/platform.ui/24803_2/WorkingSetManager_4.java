	private void restoreState(IMemento memento) {
		restoreWorkingSetState(memento);
		restoreMruList(memento);
		restoreDefaultWorkingSet(memento);
	}

