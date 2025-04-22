		tagsChangeHandler = new EventHandler() {
			public void handleEvent(Event event) {
				MUIElement element = (MUIElement) event
						.getProperty(UIEvents.EventTags.ELEMENT);
				Object newValue = event
						.getProperty(UIEvents.EventTags.NEW_VALUE);
				Object oldValue = event
						.getProperty(UIEvents.EventTags.OLD_VALUE);

				if (!(element instanceof MPart)
						|| !isBusyTagModified(oldValue, newValue)) {
					return;
				}

				MPart part = (MPart) element;
				CTabItem cti = findItemForPart(part);
				if (cti != null) {
					setCSSInfo(part, cti);
					reapplyStyles(cti);

				}
			}
		};
