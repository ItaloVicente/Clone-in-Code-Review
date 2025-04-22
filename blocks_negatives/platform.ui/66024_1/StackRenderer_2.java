		dirtyUpdater = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object objElement = event
						.getProperty(UIEvents.EventTags.ELEMENT);

				if (!(objElement instanceof MPart)) {
					return;
				}

				MPart part = (MPart) objElement;

				String attName = (String) event
						.getProperty(UIEvents.EventTags.ATTNAME);
				Object newValue = event
						.getProperty(UIEvents.EventTags.NEW_VALUE);

				MElementContainer<MUIElement> parent = part.getParent();
				if (parent != null
						&& parent.getRenderer() == StackRenderer.this) {
					CTabItem cti = findItemForPart(part, parent);
					if (cti != null) {
						updateTab(cti, part, attName, newValue);
					}
					return;
				}
