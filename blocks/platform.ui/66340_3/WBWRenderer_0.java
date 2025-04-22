	@Inject
	@Optional
	private void subscribeTopicDetachedChanged(@UIEventTopic(UIEvents.Window.TOPIC_WINDOWS) Event event) {
		if (!(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow))
			return;

		if (UIEvents.isREMOVE(event)) {
			for (Object removed : UIEvents.asIterable(event, UIEvents.EventTags.OLD_VALUE)) {
				if (removed instanceof MWindow && ((MWindow) removed).getRenderer() instanceof WBWRenderer) {
					MWindow window = (MWindow) removed;
					((WBWRenderer) window.getRenderer()).handleParentChange(window);
				}
			}
		} else if (UIEvents.isADD(event)) {
			for (Object removed : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				if (removed instanceof MWindow && ((MWindow) removed).getRenderer() instanceof WBWRenderer) {
					MWindow window = (MWindow) removed;
					((WBWRenderer) window.getRenderer()).handleParentChange(window);
				}
			}
		}
	}

	private void handleParentChange(MWindow child) {
		Shell theShell = (Shell) child.getWidget();
		if (theShell == null)
			return;

		theShell.setImage(getImage(child));
	}

