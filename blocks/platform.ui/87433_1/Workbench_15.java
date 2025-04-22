		eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_CHILDREN, event -> {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			if (!(element instanceof MApplication)) {
				return;
			}
			MApplication app = (MApplication) element;
			if (UIEvents.isREMOVE(event)) {
				if (app.getChildren().isEmpty()) {
					Object oldValue = event.getProperty(UIEvents.EventTags.OLD_VALUE);
					WorkbenchPlugin.log("The final top level window " + oldValue //$NON-NLS-1$
							+ " was just removed", new Exception()); //$NON-NLS-1$
