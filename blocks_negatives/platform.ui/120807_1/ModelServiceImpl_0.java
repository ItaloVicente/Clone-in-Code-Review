	private EventHandler hostedElementHandler = new EventHandler() {

		@Override
		public void handleEvent(Event event) {
			final MUIElement changedElement = (MUIElement) event.getProperty(EventTags.ELEMENT);
			if (!changedElement.getTags().contains(HOSTED_ELEMENT)) {
				return;
			}

			if (changedElement.getWidget() != null) {
				return;
			}

			EObject eObj = (EObject) changedElement;
			if (!(eObj.eContainer() instanceof MWindow)) {
				return;
			}

			MWindow hostingWindow = (MWindow) eObj.eContainer();
			hostingWindow.getSharedElements().remove(changedElement);
			changedElement.getTags().remove(HOSTED_ELEMENT);
		}
	};

