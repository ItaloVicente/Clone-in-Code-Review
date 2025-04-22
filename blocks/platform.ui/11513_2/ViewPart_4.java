
	@Override
	public void showBusy(boolean busy) {
		UIEvents.publishEvent(createEvent(UIEvents.UILabel.TOPIC_BUSY)
				.withParam(UIEvents.EventTags.ELEMENT, getModel())
				.withParam(UIEvents.EventTags.ATTNAME, UIEvents.UILifeCycle.BUSY)
				.withParam(UIEvents.EventTags.NEW_VALUE, busy));
	}
