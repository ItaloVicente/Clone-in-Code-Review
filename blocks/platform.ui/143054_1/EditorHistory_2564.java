	public IStatus saveState(IMemento memento) {
		Iterator iterator = fifoList.iterator();
		while (iterator.hasNext()) {
			EditorHistoryItem item = (EditorHistoryItem) iterator.next();
			if (item.canSave()) {
				IMemento itemMemento = memento.createChild(IWorkbenchConstants.TAG_FILE);
				item.saveState(itemMemento);
			}
		}
		return Status.OK_STATUS;
	}
