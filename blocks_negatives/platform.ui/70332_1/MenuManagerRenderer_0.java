	private EventHandler selectionUpdater = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
		}
	};

	private EventHandler enabledUpdater = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenuItem))
				return;

			MMenuItem itemModel = (MMenuItem) event.getProperty(UIEvents.EventTags.ELEMENT);
			IContributionItem ici = getContribution(itemModel);
			if (ici != null) {
				ici.update();
			}
		}
	};
