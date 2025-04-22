	@Inject
	@Optional
	private void subscribeSelectionUpdated(@UIEventTopic(UIEvents.Item.TOPIC_SELECTED) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenuItem))
			return;

		MMenuItem itemModel = (MMenuItem) event.getProperty(UIEvents.EventTags.ELEMENT);
		IContributionItem ici = getContribution(itemModel);
		if (ici != null) {
			ici.update();
		}
	};

