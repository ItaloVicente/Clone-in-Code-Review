		additionHandler = event -> {
			if (application == event.getProperty(UIEvents.EventTags.ELEMENT)) {
				if (UIEvents.isADD(event)) {
					for (Object obj : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
						if (obj instanceof MCommand) {
							createCommand((MCommand) obj);
						} else if (obj instanceof MCategory) {
							createCategory((MCategory) obj);
