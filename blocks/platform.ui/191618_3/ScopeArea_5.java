		if ((buttonVisibleOptions & BUTTON_ON_ANY) != 0) {
			buttons[ON_ANY] = createRadioButton(parent, MarkerMessages.filtersDialog_anyResource, ON_ANY);
		}

		if ((buttonVisibleOptions & BUTTON_ON_ANY_IN_SAME_CONTAINER) != 0) {
			buttons[ON_ANY_IN_SAME_CONTAINER] = createRadioButton(parent,
					MarkerMessages.filtersDialog_anyResourceInSameProject, ON_ANY_IN_SAME_CONTAINER);
		}

		if ((buttonVisibleOptions & BUTTON_ON_SELECTED_ONLY) != 0) {

			buttons[ON_SELECTED_ONLY] = createRadioButton(parent, MarkerMessages.filtersDialog_selectedResource,
					ON_SELECTED_ONLY);
		}

		if ((buttonVisibleOptions & BUTTON_ON_SELECTED_AND_CHILDREN) != 0) {
			buttons[ON_SELECTED_AND_CHILDREN] = createRadioButton(parent,
					MarkerMessages.filtersDialog_selectedAndChildren, ON_SELECTED_AND_CHILDREN);
		}

		if ((buttonVisibleOptions & BUTTON_ON_WORKING_SET) != 0) {
			workingSetArea = new WorkingSetArea(parent);
			buttons[ON_WORKING_SET] = workingSetArea.getRadioButton();
		}
