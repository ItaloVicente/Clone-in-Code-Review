	@Inject
	@Optional
	private void subscribeSelectionUpdated(@UIEventTopic(UIEvents.Item.TOPIC_SELECTED) Event event) {
		Object property = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(property instanceof MMenuItem)) {
			return;
		}

		MMenuItem itemModel = (MMenuItem) property;
		IContributionItem ici = getContribution(itemModel);
		if (ici != null) {
			ici.update();
		}
	}

