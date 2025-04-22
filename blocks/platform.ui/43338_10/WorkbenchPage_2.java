		List<String> workingSetNames = new ArrayList<String>(workingSets.length);
		for (IWorkingSet workingSet : workingSets) {
			workingSetNames.add(workingSet.getName());
		}
		saveMemento(IWorkbenchConstants.TAG_WORKING_SETS, IWorkbenchConstants.TAG_WORKING_SET, workingSetNames);

		getWindowModel().getPersistedState().put(ATT_AGGREGATE_WORKING_SET_ID,
				aggregateWorkingSetId);
	}

	@PreDestroy
	public void saveShowInMruPartIdsList() {
		saveMemento(IWorkbenchConstants.TAG_SHOW_IN_TIME, IWorkbenchConstants.TAG_ID, mruShowInPartIds);
	}

	private void saveMemento(String rootType, String childType, Collection<String> ids) {
		XMLMemento memento = XMLMemento.createWriteRoot(rootType);
		for (String id : ids) {
			memento.createChild(childType, id);
