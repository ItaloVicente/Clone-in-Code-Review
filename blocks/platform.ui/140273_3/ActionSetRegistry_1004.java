		}
		return null;
	}

	public IActionSetDescriptor[] getActionSets() {
		return (IActionSetDescriptor[]) children.toArray(new IActionSetDescriptor[children.size()]);
	}

	public IActionSetDescriptor[] getActionSetsFor(String partId) {
		ArrayList actionSets = (ArrayList) mapPartToActionSets.get(partId);
		if (actionSets != null) {
			return (IActionSetDescriptor[]) actionSets.toArray(new IActionSetDescriptor[actionSets.size()]);
		}

		ArrayList actionSetIds = (ArrayList) mapPartToActionSetIds.get(partId);
		if (actionSetIds == null) {
