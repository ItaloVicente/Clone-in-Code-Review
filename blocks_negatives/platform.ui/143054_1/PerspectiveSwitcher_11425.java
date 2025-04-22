
		MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);

		if (perspSwitcherToolControl == null || !(changedElement instanceof MPerspective))
			return;

		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		MWindow perspWin = modelService.getTopLevelWindowFor(changedElement);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);
		if (perspWin != switcherWin)
			return;

		MPerspective perspective = (MPerspective) changedElement;
