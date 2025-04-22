		MUIElement oldSel = (MUIElement) event.getProperty(UIEvents.EventTags.OLD_VALUE);
		if (oldSel != null) {
			hideElementRecursive(oldSel);
		}

		if (stack.getSelectedElement() != null) {
			lsr.showTab(stack.getSelectedElement());
