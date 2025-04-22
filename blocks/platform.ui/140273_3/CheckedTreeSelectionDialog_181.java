		deselectButton.addSelectionListener(listener);
		return buttonComposite;
	}

	private boolean evaluateIfTreeEmpty(Object input) {
		Object[] elements = fContentProvider.getElements(input);
		if (elements.length > 0) {
			if (fFilters != null) {
				for (int i = 0; i < fFilters.size(); i++) {
					ViewerFilter curr = (ViewerFilter) fFilters.get(i);
					elements = curr.filter(fViewer, input, elements);
				}
			}
		}
		return elements.length == 0;
	}
