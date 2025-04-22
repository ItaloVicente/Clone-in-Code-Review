		IWorkingSetUpdater2 updater = getUpdater();
		IAdaptable[] itemsArray;
		if (updater != null && !updater.isPersistable(this)) {
			itemsArray = updater.restore(this);
		} else {
			itemsArray = restoreFromMemento();
		}
		internalSetElements(itemsArray);
	}

	private IAdaptable[] restoreFromMemento() {
