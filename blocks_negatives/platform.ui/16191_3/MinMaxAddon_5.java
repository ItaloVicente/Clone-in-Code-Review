	private EventHandler perspectiveRemovedListener = new EventHandler() {
		public void handleEvent(Event event) {
			final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!(changedElement instanceof MPerspectiveStack)
					|| modelService.getTopLevelWindowFor(changedElement) == null)
				return;

			if (UIEvents.isREMOVE(event)) {
				for (Object removedElement : UIEvents.asIterable(event,
						UIEvents.EventTags.OLD_VALUE)) {
					MUIElement removed = (MUIElement) removedElement;
					String perspectiveId = removed.getElementId();
					MWindow window = modelService.getTopLevelWindowFor(changedElement);
					MTrimBar bar = modelService.getTrim((MTrimmedWindow) window, SideValue.TOP);

					List<MToolControl> toRemove = new ArrayList<MToolControl>();
					for (MUIElement child : bar.getChildren()) {
						String trimElementId = child.getElementId();
						if (child instanceof MToolControl && trimElementId.contains(perspectiveId)) {
							toRemove.add((MToolControl) child);
						}
					}
