		if (perspSwitcherToolControl == null || !(changedObj instanceof MPerspectiveStack))
			return;

		MWindow perspWin = modelService.getTopLevelWindowFor((MUIElement) changedObj);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);
		if (perspWin != switcherWin)
