		MTrimmedWindow window = (MTrimmedWindow) changedObj;
		if (window.getWidget() == null)
			return;

		if (UIEvents.isADD(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MUIElement added = (MUIElement) o;
				if (added.isToBeRendered())
					createGui(added, window.getWidget(), window.getContext());
			}
		} else if (UIEvents.isREMOVE(event)) {
			for (Object o : UIEvents.asIterable(event, UIEvents.EventTags.NEW_VALUE)) {
				MUIElement removed = (MUIElement) o;
				if (removed.getRenderer() != null)
					removeGui(removed);
