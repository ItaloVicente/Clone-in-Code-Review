		buttons[MarkerFieldFilterGroup.ON_ANY] = createRadioButton(parent,
				MarkerMessages.filtersDialog_anyResource,
				MarkerFieldFilterGroup.ON_ANY);
		buttons[MarkerFieldFilterGroup.ON_ANY_IN_SAME_CONTAINER] = createRadioButton(
				parent, MarkerMessages.filtersDialog_anyResourceInSameProject,
				MarkerFieldFilterGroup.ON_ANY_IN_SAME_CONTAINER);
		buttons[MarkerFieldFilterGroup.ON_SELECTED_ONLY] = createRadioButton(
				parent, MarkerMessages.filtersDialog_selectedResource,
				MarkerFieldFilterGroup.ON_SELECTED_ONLY);
		buttons[MarkerFieldFilterGroup.ON_SELECTED_AND_CHILDREN] = createRadioButton(
				parent, MarkerMessages.filtersDialog_selectedAndChildren,
				MarkerFieldFilterGroup.ON_SELECTED_AND_CHILDREN);
		workingSetArea = new WorkingSetArea(parent);
		buttons[MarkerFieldFilterGroup.ON_WORKING_SET] = workingSetArea.getRadioButton();
