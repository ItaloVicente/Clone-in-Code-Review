		String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
		if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
			Object obj = itemModel.getParent();
			if (!(obj instanceof MToolBar)) {
				return;
			}
			ToolBarManager parent = getManager((MToolBar) obj);
			if (itemModel.isToBeRendered()) {
				if (parent != null) {
					modelProcessSwitch(parent, itemModel);
					updateWidget(parent);
				}
			} else {
				removeElement(parent, itemModel);
				if (parent != null) {
					updateWidget(parent);
				}
