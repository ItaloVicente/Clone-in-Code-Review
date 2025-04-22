		activeChildHandler = new EventHandler() {
			@Override
			public void handleEvent(Event event) {
				Object element = event
						.getProperty(UIEvents.EventTags.NEW_VALUE);
				if (element instanceof MUIElement) {
					Object parent = event
							.getProperty(UIEvents.EventTags.ELEMENT);
					if (parent instanceof MGenericStack) {
						MUIElement uiElement = (MUIElement) element;
						IEclipseContext parentContext = getParentContext(uiElement);
						createGui(uiElement, parent, parentContext);

						if (parent instanceof MPerspectiveStack) {
							MPerspective perspective = (MPerspective) uiElement;
							adjustPlaceholders(perspective);
							parentContext.get(EPartService.class)
									.switchPerspective(perspective);
						}
