		additionHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object elementObj = event.getProperty(UIEvents.EventTags.ELEMENT);
				if (elementObj instanceof MBindingContext) {
					if (UIEvents.isADD(event)) {
						for (Object newObj : UIEvents.asIterable(event,
								UIEvents.EventTags.NEW_VALUE)) {
							if (newObj instanceof MBindingContext) {
								MBindingContext newCtx = (MBindingContext) newObj;
								defineContexts((MBindingContext) elementObj, newCtx);
							}
