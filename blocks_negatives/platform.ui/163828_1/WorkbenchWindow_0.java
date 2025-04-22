		try {
			getShell().setLayoutDeferred(true);
			eventBroker.send(UIEvents.REQUEST_ENABLEMENT_UPDATE_TOPIC, UIEvents.ALL_ELEMENT_ID);
			getCoolBarManager2().update(false);
		} finally {
			getShell().setLayoutDeferred(false);
		}
