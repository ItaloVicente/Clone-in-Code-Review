	@Inject
	@Optional
	private void subscribeMenuVisibilityChanged(@UIEventTopic(UIEvents.UIElement.TOPIC_VISIBLE) Event event) {
		menuSubscription(event);
	}
	
	@Inject 
	@Optional
	private void subscribeMenuTopicChanged(@UIEventTopic(UIEvents.UIElement.TOPIC_TOBERENDERED) Event event) {
		menuSubscription(event);
	}
	
	private void menuSubscription(Event event) {
