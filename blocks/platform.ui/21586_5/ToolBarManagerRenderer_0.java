	@Inject
	@Optional
	void updateRequest(
			@UIEventTopic(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC) Event eventData) {
		final Object v = eventData.getProperty(IEventBroker.DATA);
		Selector s;
		if (v instanceof Selector) {
			s = (Selector) v;
		} else {
			if (v == null || UIEvents.ALL_ELEMENT_ID.equals(v)) {
				s = new Selector() {

					public boolean select(MApplicationElement element) {
						return true;
					}
				};
			} else {
				s = new Selector() {

					public boolean select(MApplicationElement element) {
						return v.equals(element.getElementId());
					}
				};
			}
		}

		getUpdater().updateContributionItems(s);
	}

