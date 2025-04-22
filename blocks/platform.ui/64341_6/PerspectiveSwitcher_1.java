	private boolean ignoreEvent(Object changedObj) {
		if (perspSwitcherToolControl == null || perspSwitcherToolbar.isDisposed()) {
			return true;
		}

		MWindow perspWin = modelService.getTopLevelWindowFor((MUIElement) changedObj);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);

		if (perspWin != switcherWin) {
			return true;
		}

		return false;
	}

