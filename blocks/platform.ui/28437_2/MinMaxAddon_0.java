	@Inject
	@Optional
	private void subscribeTopicPerspReset(
			@UIEventTopic(UIEvents.UILifeCycle.PERSPECTIVE_RESET) Event event) {
		final MPerspective resetPersp = (MPerspective) event.getProperty(EventTags.ELEMENT);

		List<MUIElement> minimizedElements = modelService.findElements(resetPersp, null,
				MUIElement.class, Arrays.asList(IPresentationEngine.MINIMIZED));
		for (MUIElement element : minimizedElements) {
			createTrim(element);
		}
	}

