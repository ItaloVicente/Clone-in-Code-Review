		EventHandler eventHandler = event -> {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (element != null && element == getTrimStatus((WorkbenchWindow) window)) {
				ICommandService commandService = window.getService(ICommandService.class);
				Map<String, WorkbenchWindow> filter = new HashMap<>();
				filter.put(IServiceScopes.WINDOW_SCOPE, (WorkbenchWindow) window);
				commandService.refreshElements(COMMAND_ID_TOGGLE_STATUSBAR, filter);
