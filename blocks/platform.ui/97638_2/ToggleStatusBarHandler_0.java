	private void initializeEventHandler(IWorkbenchWindow window) {
		final IEventBroker eventBroker = window.getService(IEventBroker.class);
		eventBrokers.put(window, eventBroker);
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
				if (element != null && element == getTrimStatus((WorkbenchWindow) window)) {
					ICommandService commandService = window.getService(ICommandService.class);
					Map<String, WorkbenchWindow> filter = new HashMap<>();
					filter.put(IServiceScopes.WINDOW_SCOPE, (WorkbenchWindow) window);
					commandService.refreshElements(COMMAND_ID_TOGGLE_STATUSBAR, filter);
				}
			}
		};
		eventHandlers.put(window, eventHandler);
		eventBroker.subscribe(UIEvents.UIElement.TOPIC_VISIBLE, eventHandler);
	}

	@Override
	public void dispose() {
		for (IWorkbenchWindow w : eventHandlers.keySet()) {
			IEventBroker eventBroker = eventBrokers.get(w);
			EventHandler eventHandler = eventHandlers.get(w);
			if (eventBroker != null && eventHandler != null) {
				eventBroker.unsubscribe(eventHandler);
			}
		}
		super.dispose();
	}

