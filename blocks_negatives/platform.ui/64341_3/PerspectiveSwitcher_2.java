		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

		MWindow perspWin = modelService.getTopLevelWindowFor(changedElement);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);
		if (perspWin != switcherWin)
			return;
