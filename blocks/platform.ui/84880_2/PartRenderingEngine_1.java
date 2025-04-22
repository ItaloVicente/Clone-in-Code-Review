		handleElement(event, changedObj, ((MContext) changedObj).getContext());
	}

	@Inject
	@Optional
	private void subscribePartTrimHandler(@EventTopic(UIEvents.Part.TOPIC_TRIMBARS) Event event) {

		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changedObj instanceof MPart)) {
			return;
		}

		handleElement(event, changedObj, ((MContext) changedObj).getContext());
	}

	@Inject
	@Optional
	private void subscribePerspectiveTrimHandler(@EventTopic(UIEvents.Perspective.TOPIC_TRIMBARS) Event event) {

		Object changedObj = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changedObj instanceof MPerspective)) {
			return;
		}

		handleElement(event, changedObj, ((MContext) changedObj).getContext());
	}

	private void handleElement(Event event, Object changedObj, IEclipseContext elementContext) {
		MUIElement uiElement = (MUIElement) changedObj;
		if (uiElement.getWidget() == null) {
