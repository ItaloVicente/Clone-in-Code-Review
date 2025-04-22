	private void restoreFocus(MPart part, ISelection selection) {
		Boolean containsSelection = (Boolean) part.getTransientData().get(CONTAINS_SELECTION_PROP);
		boolean emptySelection = selection == StructuredSelection.EMPTY;
		
		if (containsSelection != null && !containsSelection && !emptySelection) {
			part.getContext().get(IPresentationEngine.class).focusGui(part);
		}
		
		part.getTransientData().put(CONTAINS_SELECTION_PROP, emptySelection);
	}

