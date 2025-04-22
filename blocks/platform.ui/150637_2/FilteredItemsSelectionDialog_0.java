	private List<Object> prepareInitialSelection(List<Object> currentSelection) {
		boolean hasNoInitialSelection = getInitialElementSelections().isEmpty();
		if (hasNoInitialSelection) {
			return currentSelection;
		}
		refreshWithLastSelection = true;
		if (!multi) {
			Object firstSelectedItem = getInitialElementSelections().get(0);
			return Collections.singletonList(firstSelectedItem);
		}
		return getInitialElementSelections();
	}

