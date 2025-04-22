		};

		eventBroker.subscribe(UIEvents.buildTopic(UIEvents.Dirtyable.TOPIC,
				UIEvents.Dirtyable.DIRTY), dirtyUpdater);

		viewMenuUpdater = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object objElement = event
						.getProperty(UIEvents.EventTags.ELEMENT);

				if (!(objElement instanceof MMenuElement)) {
					return;
				}
