	protected void hookWorkbenchListeners() {
		shellListener = new EventHandler() {
			public void handleEvent(org.osgi.service.event.Event event) {
				if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow
						&& event.getProperty(UIEvents.EventTags.NEW_VALUE) != null) {
					MWindow window = (MWindow) event
							.getProperty(UIEvents.EventTags.ELEMENT);
					modifyWindowShell(window);
					updateFullScreenStatus(window);
				}
			}
		};
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_WIDGET, shellListener);

		menuListener = new EventHandler() {
			public void handleEvent(org.osgi.service.event.Event event) {
				Object newValue = event
						.getProperty(UIEvents.EventTags.NEW_VALUE);
				Object oldValue = event
						.getProperty(UIEvents.EventTags.OLD_VALUE);
				Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
				if (element instanceof MWindow && oldValue == null
						&& newValue instanceof MMenu) {
					modifyWindowShell((MWindow) element);
				}
			}
		};
		eventBroker.subscribe(UIEvents.Window.TOPIC_MAINMENU, menuListener);

		menuContributionListener = new EventHandler() {
			public void handleEvent(org.osgi.service.event.Event event) {
				if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MMenuContribution
						&& event.getProperty(UIEvents.EventTags.NEW_VALUE) != null) {
					MMenuContribution contribution = (MMenuContribution) event
							.getProperty(UIEvents.EventTags.ELEMENT);
					processMenuContribution(contribution);
				}
			}
		};
		eventBroker.subscribe(
				UIEvents.MenuContributions.TOPIC_MENUCONTRIBUTIONS,
				menuContributionListener);

		commandListener = new EventHandler() {
			public void handleEvent(org.osgi.service.event.Event event) {
				if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MCommand) {
					MCommand cmd = (MCommand) event
							.getProperty(UIEvents.EventTags.ELEMENT);
					String id = cmd.getElementId();
					if (COMMAND_ID_ABOUT.equals(id)
							|| COMMAND_ID_PREFERENCES.equals(id)
							|| COMMAND_ID_QUIT.equals(id)) {
						hookApplicationMenu();
					}
				}
			}
		};
		eventBroker.subscribe(UIEvents.Application.TOPIC_COMMANDS,
				commandListener);

		tagListener = new EventHandler() {
			public void handleEvent(org.osgi.service.event.Event event) {
				if (event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MWindow) {
					MWindow window = (MWindow) event
							.getProperty(UIEvents.EventTags.ELEMENT);
					updateFullScreenStatus(window);
				}
