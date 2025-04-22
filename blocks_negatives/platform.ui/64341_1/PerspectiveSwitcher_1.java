		if (event == null)
			return;
		if (perspSwitcherToolbar.isDisposed()) {
			return;
		}

		MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
