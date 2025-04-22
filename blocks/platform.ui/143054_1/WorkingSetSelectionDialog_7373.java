	private void restoreAddedWorkingSets() {
		IWorkingSetManager manager = WorkbenchPlugin.getDefault().getWorkingSetManager();
		Iterator iterator = getAddedWorkingSets().iterator();

		while (iterator.hasNext()) {
			manager.removeWorkingSet(((IWorkingSet) iterator.next()));
		}
	}

	private void restoreChangedWorkingSets() {
		Iterator iterator = getEditedWorkingSets().keySet().iterator();

		while (iterator.hasNext()) {
			IWorkingSet editedWorkingSet = (IWorkingSet) iterator.next();
