	protected void restoreWorkingSetState(IMemento memento) {
		IMemento[] children = memento.getChildren(IWorkbenchConstants.TAG_WORKING_SET);

		for (int i = 0; i < children.length; i++) {
			AbstractWorkingSet workingSet = (AbstractWorkingSet) restoreWorkingSet(children[i]);
			if (workingSet != null) {
				internalAddWorkingSet(workingSet);
			}
		}
	}
