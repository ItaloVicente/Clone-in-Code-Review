	private boolean relevantEvent(Event event, boolean perspectiveStack) {
		if (event == null)
			return false;

		if (perspSwitcherToolControl == null || perspSwitcherToolbar.isDisposed()) {
			return false;
		}

		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);


		if (perspectiveStack) {
			if (!(changedObj instanceof MPerspectiveStack)) {
				return false;
			}
		} else // check if the element is an instance of MPerspective
		{
			if (!(changedObj instanceof MPerspective)) {
				return false;
			}
		}

		MWindow perspWin = modelService.getTopLevelWindowFor((MUIElement) changedObj);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);

		if (perspWin != switcherWin) {
			return false;
		}

		return true;
	}

