		statusReporter.get().report(new Status(IStatus.WARNING, CocoaUIProcessor.FRAGMENT_ID,
				"Exception occurred during CocoaUI processing", e), StatusReporter.LOG); //$NON-NLS-1$
	}


	@Inject
	@Optional
	private void monitorShellTopicChanges(@UIEventTopic(UIEvents.UIElement.TOPIC_WIDGET) Event event) {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow
				&& event.getProperty(UIEvents.EventTags.NEW_VALUE) != null) {
			MWindow window = (MWindow) event.getProperty(UIEvents.EventTags.ELEMENT);
			modifyWindowShell(window);
			updateFullScreenStatus(window);
		}
