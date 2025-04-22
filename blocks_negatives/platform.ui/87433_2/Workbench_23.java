		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, new EventHandler() {
			@Override
			public void handleEvent(org.osgi.service.event.Event event) {
				if (application == event.getProperty(UIEvents.EventTags.ELEMENT)) {
					if (UIEvents.isREMOVE(event)) {
						for (Object removed : UIEvents.asIterable(event,
								UIEvents.EventTags.OLD_VALUE)) {
							MWindow window = (MWindow) removed;
							IEclipseContext windowContext = window.getContext();
							if (windowContext != null) {
								IWorkbenchWindow wwindow = windowContext.get(IWorkbenchWindow.class);
								if (wwindow != null) {
									fireWindowClosed(wwindow);
								}
