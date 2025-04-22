		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, event -> {
			if (application == event.getProperty(UIEvents.EventTags.ELEMENT)) {
				if (UIEvents.EventTypes.SET.equals(event
						.getProperty(UIEvents.EventTags.TYPE))) {
					MWindow window = (MWindow) event.getProperty(UIEvents.EventTags.NEW_VALUE);
					if (window != null) {
						IWorkbenchWindow wwindow = window.getContext().get(IWorkbenchWindow.class);
						if (wwindow != null) {
							e4Context.set(ISources.ACTIVE_WORKBENCH_WINDOW_NAME, wwindow);
							e4Context.set(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME,
									wwindow.getShell());
