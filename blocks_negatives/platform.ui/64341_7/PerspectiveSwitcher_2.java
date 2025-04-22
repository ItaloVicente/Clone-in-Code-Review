
		MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);

		if (perspSwitcherToolControl == null || !(changedElement instanceof MPerspective))
			return;

		MWindow perspWin = modelService.getTopLevelWindowFor(changedElement);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);
		if (perspWin != switcherWin)
			return;

		MPerspective persp = (MPerspective) changedElement;
