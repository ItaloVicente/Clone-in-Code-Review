		if (e.widget != widget) {
			return; // Not for us.
		}
		if (action.getStyle() == IAction.AS_DROP_DOWN_MENU && menuCreatorCalled) {
			IMenuCreator mc = action.getMenuCreator();
			if (mc != null) {
				mc.dispose();
