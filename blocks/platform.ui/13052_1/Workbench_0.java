		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new EventHandler() {
			public void handleEvent(org.osgi.service.event.Event event) {
				forceOpenPerspective();
				eventBroker.unsubscribe(this);
			}
		});
