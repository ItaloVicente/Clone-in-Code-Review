		MWindow perspWin = modelService.getTopLevelWindowFor(changedElement);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);
		if (perspWin != switcherWin)
			return;

		MPerspective perspective = (MPerspective) changedElement;
