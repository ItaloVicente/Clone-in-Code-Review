	private EventHandler toBeRenderedUpdater = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			String attName = (String) event
					.getProperty(UIEvents.EventTags.ATTNAME);
			if (element instanceof MMenuItem) {
				MMenuItem itemModel = (MMenuItem) element;
				if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
					Object obj = itemModel.getParent();
					if (!(obj instanceof MMenu)) {
						return;
