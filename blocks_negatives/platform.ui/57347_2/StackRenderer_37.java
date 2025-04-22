		stylingHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				MUIElement changed = (MUIElement) event
						.getProperty(UIEvents.EventTags.ELEMENT);
				if (!(changed instanceof MPart))
					return;
