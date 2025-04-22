		if (eventHandler == null) {
			eventBroker = window.getService(IEventBroker.class);
			eventHandler = new EventHandler() {
				@Override
				public void handleEvent(Event event) {
					Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
					if (element instanceof MUIElement && ((MUIElement) element).getElementId().equals(BOTTOM_TRIM_ID)) {
						ICommandService commandService = window.getService(ICommandService.class);
						Map<String, WorkbenchWindow> filter = new HashMap<>();
						filter.put(IServiceScopes.WINDOW_SCOPE, (WorkbenchWindow) window);
						commandService.refreshElements(COMMAND_ID_TOGGLE_STATUSBAR, filter);
					}
				}
			};
			eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, eventHandler);
		}
