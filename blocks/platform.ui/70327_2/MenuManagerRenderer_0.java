	@Inject
	@Optional
	private void subscribeUILabelTopicAll(@UIEventTopic(UIEvents.UILabel.TOPIC_ALL) Event event) {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (element instanceof MMenuItem) {
			handleLabelOfMenuItem(event, element);
		}
