
		childHandler = new EventHandler() {
			public void handleEvent(Event event) {
				Object changedObj = event
						.getProperty(UIEvents.EventTags.ELEMENT);
				if (!(changedObj instanceof MPartStack))
					return;
				MPartStack stack = (MPartStack) changedObj;

				if (UIEvents.isADD(event)) {
					for (Object o : UIEvents.asIterable(event,
							UIEvents.EventTags.NEW_VALUE)) {
						MUIElement added = (MUIElement) o;
						if (added == activePart) {
							styleStack(stack, true);
						}
					}
				} else if (UIEvents.isREMOVE(event)) {
					Activator.trace(Policy.DEBUG_RENDERER,
							"Child Removed", null); //$NON-NLS-1$
					for (Object o : UIEvents.asIterable(event,
							UIEvents.EventTags.OLD_VALUE)) {
						MUIElement removed = (MUIElement) o;
						if (removed == activePart) {
							styleStack(stack, false);
						}
					}
				}
			}
		};

		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN,
				childHandler);
