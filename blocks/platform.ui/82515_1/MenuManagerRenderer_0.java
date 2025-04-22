	@Inject
	@Optional
	private void subscribeItemEnabledUpdate(@UIEventTopic(UIEvents.Item.TOPIC_ENABLED) Event event) {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(element instanceof MMenuItem)) {
			return;
		}

		MMenuItem itemModel = (MMenuItem) event.getProperty(UIEvents.EventTags.ELEMENT);
		IContributionItem ici = getContribution(itemModel);
		if (ici != null) {
			ici.update();
		}
	}

