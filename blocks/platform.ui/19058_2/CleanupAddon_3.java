	@Inject
	@Optional
	private void subscribeVisibilityChanged(
			@UIEventTopic(UIEvents.UIElement.TOPIC_VISIBLE) Event event) {
		MUIElement changedObj = (MUIElement) event.getProperty(UIEvents.EventTags.ELEMENT);
		if (changedObj instanceof MTrimBar || ((Object) changedObj.getParent()) instanceof MToolBar)
			return;

		if (changedObj.getWidget() instanceof Shell) {
			((Shell) changedObj.getWidget()).setVisible(changedObj.isVisible());
		} else if (changedObj.getWidget() instanceof Rectangle) {
			if (changedObj.isVisible()) {
				MUIElement parent = changedObj.getParent();
				if (!parent.isVisible())
					parent.setVisible(true);
			} else {
				MElementContainer<MUIElement> parent = changedObj.getParent();
				boolean makeInvisible = true;
				for (MUIElement kid : parent.getChildren()) {
					if (kid.isToBeRendered() && kid.isVisible()) {
						makeInvisible = false;
						break;
					}
				}
				if (makeInvisible)
					parent.setVisible(false);
			}
		} else if (changedObj.getWidget() instanceof Control) {
			Control ctrl = (Control) changedObj.getWidget();
			MElementContainer<MUIElement> parent = changedObj.getParent();
			if (parent == null || ((Object) parent) instanceof MToolBar) {
