		toBeRenderedHandler = event -> {
			MUIElement element = (MUIElement) event
					.getProperty(UIEvents.EventTags.ELEMENT);
			Boolean value = (Boolean) event
					.getProperty(UIEvents.EventTags.NEW_VALUE);
			if (value.booleanValue()) {
				createGui(element, element.getParent(),
						getParentContext(element));
			} else {
				removeGui(element);
