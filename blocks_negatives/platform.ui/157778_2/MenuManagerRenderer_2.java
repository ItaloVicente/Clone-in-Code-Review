		if (UIEvents.UIElement.VISIBLE.equals(attName)) {
			if (element instanceof MMenu) {
				MMenu menuModel = (MMenu) element;
				MenuManager manager = getManager(menuModel);
				if (manager == null) {
					return;
				}
				boolean visible = menuModel.isVisible();
				manager.setVisible(visible);
				if (manager.getParent() != null) {
					manager.getParent().markDirty();
					scheduleManagerUpdate(manager.getParent());
				}
				if (menuModel.getParent() == null) {
					if (menuModel instanceof MPopupMenu) {
						Object data = menuModel.getTransientData().get(IPresentationEngine.RENDERING_PARENT_KEY);
						if (data instanceof Control) {
							Menu menu = (Menu) menuModel.getWidget();
							if (visible && menuModel.isToBeRendered() && menu != null && !menu.isDisposed()) {
								((Control) data).setMenu(menu);
							}
							if (!visible) {
								((Control) data).setMenu(null);
							}
