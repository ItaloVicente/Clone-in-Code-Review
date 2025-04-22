	@Inject
	@Optional
	private void handleMinimizedStacks(
			@UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {
		Object changedObj = event.getProperty(EventTags.ELEMENT);

		if (!(changedObj instanceof MToolControl))
			return;

		final MToolControl minimizedStack = (MToolControl) changedObj;

		if (!(minimizedStack.getObject() instanceof TrimStack))
			return;

		TrimStack ts = (TrimStack) minimizedStack.getObject();
		if (!(ts.getMinimizedElement() instanceof MPartStack))
			return;

		MPartStack stack = (MPartStack) ts.getMinimizedElement();
		MUIElement stackSel = stack.getSelectedElement();
		MPart thePart = null;
		if (stackSel instanceof MPart) {
			thePart = (MPart) stackSel;
		} else if (stackSel instanceof MPlaceholder) {
			MPlaceholder ph = (MPlaceholder) stackSel;
			if (ph.getRef() instanceof MPart) {
				thePart = (MPart) ph.getRef();
			}
		}

		if (thePart == null)
			return;

		if (UIEvents.isADD(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
					TrimStack.MINIMIZED_AND_SHOWING)) {
				firePartVisible(thePart);
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
					TrimStack.MINIMIZED_AND_SHOWING)) {
				firePartHidden(thePart);
			}
		}
	}

