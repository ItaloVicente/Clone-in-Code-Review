	private EventHandler toBeRenderedUpdater = event -> {
		Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
		String attName = (String) event
				.getProperty(UIEvents.EventTags.ATTNAME);
		if (element instanceof MMenuItem) {
			MMenuItem itemModel1 = (MMenuItem) element;
			if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
				Object obj1 = itemModel1.getParent();
				if (!(obj1 instanceof MMenu)) {
					return;
				}
				MenuManager parent = getManager((MMenu) obj1);
				if (itemModel1.isToBeRendered()) {
					if (parent != null) {
						modelProcessSwitch(parent, itemModel1);
