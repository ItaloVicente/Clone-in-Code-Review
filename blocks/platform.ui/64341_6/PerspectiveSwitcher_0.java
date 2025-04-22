		MPerspective selElement = perspStack.getSelectedElement();
		for (ToolItem ti : perspSwitcherToolbar.getItems()) {
			ti.setSelection(ti.getData() == selElement);
		}
	}


	@Inject
	@Optional
	void handleToBeRenderedEvent(@UIEventTopic(UIEvents.UIElement.TOPIC_TOBERENDERED) Event event) {

		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changedObj instanceof MPerspective) || (ignoreEvent(changedObj))) {
