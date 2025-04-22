	@Inject
	@Optional
	private void monitorMainMenuTopicChanges(@UIEventTopic(UIEvents.Window.TOPIC_MAINMENU) Event event) {
		Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);
		Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		if (element instanceof MWindow && oldValue == null && newValue instanceof MMenu) {
			modifyWindowShell((MWindow) element);
		}
	}

	@Inject
	@Optional
	private void monitorMenuContributionsChanges(
			@UIEventTopic(UIEvents.MenuContributions.TOPIC_MENUCONTRIBUTIONS) Event event) {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenuContribution
				&& event.getProperty(UIEvents.EventTags.NEW_VALUE) != null) {
			MMenuContribution contribution = (MMenuContribution) event.getProperty(UIEvents.EventTags.ELEMENT);
			processMenuContribution(contribution);
		}
	}

	@Inject
	@Optional
	private void monitorCommandChanges(@UIEventTopic(UIEvents.Application.TOPIC_COMMANDS) Event event) {
		if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MCommand) {
			MCommand cmd = (MCommand) event.getProperty(UIEvents.EventTags.ELEMENT);
			String id = cmd.getElementId();
			if (COMMAND_ID_ABOUT.equals(id) || COMMAND_ID_PREFERENCES.equals(id) || COMMAND_ID_QUIT.equals(id)) {
				hookApplicationMenu();
