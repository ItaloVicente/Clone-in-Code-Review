	@Inject
	void handleSelectionEvent(@Optional @UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) Event event) {
		if (event == null)
			return;
		if (perspSwitcherToolbar.isDisposed()) {
			return;
		}

		MUIElement changedElement = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);

		if (perspSwitcherToolControl == null || !(changedElement instanceof MPerspectiveStack))
			return;

		MWindow perspWin = modelService.getTopLevelWindowFor(changedElement);
		MWindow switcherWin = modelService.getTopLevelWindowFor(perspSwitcherToolControl);
		if (perspWin != switcherWin)
			return;

		MPerspectiveStack perspStack = (MPerspectiveStack) changedElement;
		if (!perspStack.isToBeRendered())
			return;

		MPerspective selElement = perspStack.getSelectedElement();
		for (ToolItem ti : perspSwitcherToolbar.getItems()) {
			ti.setSelection(ti.getData() == selElement);
		}
	}

