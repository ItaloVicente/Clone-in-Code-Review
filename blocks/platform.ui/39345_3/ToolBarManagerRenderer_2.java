		MToolBarElement itemModel = (MToolBarElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
		if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
			Object obj = itemModel.getParent();
			if (!(obj instanceof MToolBar)) {
				return;
			}
			ToolBarManager parent = getManager((MToolBar) obj);
			if (itemModel.isToBeRendered()) {
