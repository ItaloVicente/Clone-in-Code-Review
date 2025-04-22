		childHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				if (UIEvents.isADD(event)) {
					for (Object element : UIEvents.asIterable(event,
							UIEvents.EventTags.NEW_VALUE)) {
						if (element instanceof MUIElement) {
							Object parent = event
									.getProperty(UIEvents.EventTags.ELEMENT);
							IEclipseContext parentContext = getParentContext((MUIElement) element);
							if (element instanceof MContext) {
								IEclipseContext context = ((MContext) element)
										.getContext();
								if (context != null
										&& context.getParent() != parentContext) {
									context.deactivate();
								}
