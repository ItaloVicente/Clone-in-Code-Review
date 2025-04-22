	private EventHandler widgetHandler = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			Object newValue = event.getProperty(UIEvents.EventTags.NEW_VALUE);

			if (element instanceof MArea) {
				if (modelService.findElements(window, null, MArea.class, null).contains(element)) {
					if (newValue instanceof Control) {
						installAreaDropSupport((Control) newValue);
					}
				}
			} else if (element instanceof MPart && newValue == null) {
				MPart changedPart = (MPart) element;
				Object impl = changedPart.getObject();
				if (impl != null && !(impl instanceof CompatibilityPart)) {
					EditorReference eRef = getEditorReference(changedPart);
					if (eRef != null)
						editorReferences.remove(eRef);
					ViewReference vRef = getViewReference(changedPart);
					if (vRef != null)
						viewReferences.remove(vRef);
