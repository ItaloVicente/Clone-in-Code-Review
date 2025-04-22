	@Inject
	@Optional
	private void subscribeTopicIconUriChanged(@UIEventTopic(UIEvents.UILabel.TOPIC_ICONURI) Event event) {
		if (trimStackTB == null || trimStackTB.isDisposed() || minimizedElement.getWidget() == null) {
			return;
		}

		Object changedElement = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(changedElement instanceof MUIElement)) {
			return;
		}

		ToolItem toolItem = getChangedToolItem((MUIElement) changedElement);
		if (toolItem != null) {
			toolItem.setImage(getImage((MUILabel) toolItem.getData()));
		}
	}

