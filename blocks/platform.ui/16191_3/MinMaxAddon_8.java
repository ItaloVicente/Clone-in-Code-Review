	@Inject
	@Optional
	private void subscribeTopicPerspSaved(
			@UIEventTopic(UIEvents.UILifeCycle.PERSPECTIVE_SAVED) Event event) {
		final MPerspective savedPersp = (MPerspective) event.getProperty(EventTags.ELEMENT);
		String cache = getTrimCache(savedPersp);
		minMaxAddon.getPersistedState().put(savedPersp.getElementId(), cache);
	}

	private String getTrimCache(MPerspective savedPersp) {
		MWindow topWin = modelService.getTopLevelWindowFor(savedPersp);
		String perspIdStr = '(' + savedPersp.getElementId() + ')';

		String cache = getWinCache(topWin, perspIdStr);
		for (MWindow dw : savedPersp.getWindows()) {
			cache += getWinCache(dw, perspIdStr);
