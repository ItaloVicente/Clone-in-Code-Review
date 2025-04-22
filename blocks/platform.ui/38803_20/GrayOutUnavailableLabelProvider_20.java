		if (element instanceof DisplayItem) {
			if (!CustomizePerspectiveDialog.isEffectivelyAvailable((DisplayItem) element, filter)) {
				return display.getSystemColor(SWT.COLOR_GRAY);
			}
		}
		if (element instanceof ActionSet) {
			if (!((ActionSet) element).isActive()) {
				return display.getSystemColor(SWT.COLOR_GRAY);
			}
