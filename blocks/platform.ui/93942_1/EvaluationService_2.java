		if (ENABLED_OPTIMIZATION) {
			eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
		} else {
			eventBroker.post(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
		}

