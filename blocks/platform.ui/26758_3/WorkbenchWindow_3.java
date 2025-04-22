	private static final String QUICK_ACCESS_ID = "SearchField"; //$NON-NLS-1$
	private static final String QUICK_ACCESS_HIDDEN = "QUICK_ACCESS_HIDDEN"; //$NON-NLS-1$

	@Inject
	private void hideQuickAccess(
			@Optional @UIEventTopic(UIEvents.ApplicationElement.TOPIC_TAGS) Event event) {
		if (event == null) {
			return;
		}
		Object origin = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (!(origin instanceof MToolControl)) {
			return;
		}
		MToolControl control = (MToolControl) origin;
		if (!QUICK_ACCESS_ID.equals(control.getElementId())) {
			return;
		}
		if (UIEvents.isADD(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.NEW_VALUE,
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				if (!model.getTags().contains(QUICK_ACCESS_HIDDEN)) {
					model.getTags().add(QUICK_ACCESS_HIDDEN);
				}
			}
		} else if (UIEvents.isREMOVE(event)) {
			if (UIEvents.contains(event, UIEvents.EventTags.OLD_VALUE,
					IPresentationEngine.HIDDEN_EXPLICITLY)) {
				model.getTags().remove(QUICK_ACCESS_HIDDEN);
			}
		}
	}

