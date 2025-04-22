
	@Override
	public void showBusy(boolean busy) {
		MPart model = getModel();
		IEventBroker eventBroker = getEventBroker(model);

		if (eventBroker != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(UIEvents.EventTags.ELEMENT, model);
			data.put(UIEvents.EventTags.ATTNAME, UIEvents.UILabel.BUSY);
			data.put(UIEvents.EventTags.NEW_VALUE, busy);

			eventBroker.send(UIEvents.UILabel.TOPIC_BUSY, data);
		}
	}

	private IEventBroker getEventBroker(MPart model) {
		if (eventBroker == null && model != null) {
			eventBroker = model.getContext().get(IEventBroker.class);
		}
		return eventBroker;
	}
