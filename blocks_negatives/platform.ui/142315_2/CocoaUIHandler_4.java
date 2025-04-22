	/** Watch for a window's full-screen tag being flipped */
	@Inject
	@Optional
	private void monitorApplicationTagChanges(@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow) {
			MWindow window = (MWindow) event.getProperty(UIEvents.EventTags.ELEMENT);
			updateFullScreenStatus(window);
		}
	}

	/**
	 * @param window
	 */
	protected void updateFullScreenStatus(MWindow window) {
		if (OS.VERSION < 0x1070 || !(window.getWidget() instanceof Shell)) {
			return;
		}
	}

