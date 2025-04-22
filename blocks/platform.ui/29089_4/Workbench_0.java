		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				advisor.postStartup(); // May trigger a close/restart.
			}
		});

