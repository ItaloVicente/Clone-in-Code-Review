		MWindow perspWin = modelService.getTopLevelWindowFor(changedElement);
		MWindow switcherWin = modelService.getTopLevelWindowFor(psME);
		if (perspWin != switcherWin)
			return;

		MPerspective perspective = (MPerspective) changedElement;
		if (!perspective.isToBeRendered())
			return;

		for (ToolItem ti : psTB.getItems()) {
			if (ti.getData() == perspective) {
				updateToolItem(ti, attName, newValue);
