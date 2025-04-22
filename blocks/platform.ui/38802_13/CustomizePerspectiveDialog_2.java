	private boolean updateHiddenElements(List<ActionSet> items, String currentHidden, String prefix) {
		List<String> changedAndVisible = new ArrayList<String>();
		List<String> changedAndInvisible = new ArrayList<String>();
		for (ActionSet actionSet : items) {
			if (!actionSet.wasChanged()) {
				continue;
			}
			if (actionSet.isActive()) {
				changedAndVisible.add(actionSet.descriptor.getId());
			} else {
				changedAndInvisible.add(actionSet.descriptor.getId());
			}
		}
		return updateHiddenElements(currentHidden, prefix, changedAndVisible, changedAndInvisible);
	}
