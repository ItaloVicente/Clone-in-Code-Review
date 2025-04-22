		if (oldInput == null && input == null)
			return;
		if (input == null && stackLayout.topControl != noInputText) {
			stackLayout.topControl = noInputText;
			getTable().getParent().layout(false);
		} else if (input != null && stackLayout.topControl != getTable()) {
			stackLayout.topControl = getTable();
			getTable().getParent().layout(false);
