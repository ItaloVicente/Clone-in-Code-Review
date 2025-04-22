	@Inject
	@Optional
	private void subscribeItemTopicSelected(@UIEventTopic(UIEvents.Item.TOPIC_SELECTED) Event event) {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(element instanceof MMenuItem)) {
			return;
		}
		MMenuItem itemModel = (MMenuItem) element;
		IContributionItem ici = getContribution(itemModel);
		if (ici != null) {
			ici.update();
		}
	}


