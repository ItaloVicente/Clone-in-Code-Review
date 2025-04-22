		boolean inputChanged;
		if (oldInput == null && input == null) {
			inputChanged = false;
		} else if (oldInput == null || input == null) {
			inputChanged = true;
		} else {
			inputChanged = !input.equals(oldInput);
		}
		if (inputChanged) {
			if (input == null && stackLayout.topControl != noInputText) {
				stackLayout.topControl = noInputText;
				getTable().getParent().layout(false);
			} else if (input != null && stackLayout.topControl != getTable()) {
				stackLayout.topControl = getTable();
				getTable().getParent().layout(false);
			}
			super.inputChanged(input, oldInput);
