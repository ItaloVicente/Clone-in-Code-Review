	private Object addAssociation(String actionSetId, String partId) {
		ArrayList actionSets = (ArrayList) mapPartToActionSetIds.get(partId);
		if (actionSets == null) {
			actionSets = new ArrayList();
			mapPartToActionSetIds.put(partId, actionSets);
		}
		actionSets.add(actionSetId);

		ActionSetPartAssociation association = new ActionSetPartAssociation(partId, actionSetId);
		return association;
	}

	public IActionSetDescriptor findActionSet(String id) {
		Iterator i = children.iterator();
		while (i.hasNext()) {
			IActionSetDescriptor desc = (IActionSetDescriptor) i.next();
			if (desc.getId().equals(id)) {
