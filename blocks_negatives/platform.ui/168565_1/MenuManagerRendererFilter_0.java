	}

	private void safeHandleEvent(Event event) {
		if (!(event.widget instanceof Menu)) {
			return;
		}
		final Menu menu = (Menu) event.widget;
		if ((menu.getStyle() & SWT.BAR) != 0) {
			return;
		}
		if (event.type == SWT.Dispose) {
			if (Policy.DEBUG_MENUS) {
				trace("handleMenu.Dispose", menu, null); //$NON-NLS-1$
			}
			cleanUp(menu, null, null);
			return;
		}

		MenuManager menuManager = null;
		Object obj = menu.getData(AbstractPartRenderer.OWNING_ME);
		if (obj == null) {
			Object tmp = menu.getData(MenuManager.MANAGER_KEY);
			if (tmp instanceof MenuManager) {
				MenuManager tmpManager = (MenuManager) tmp;
				menuManager = tmpManager;
				obj = renderer.getMenuModel(tmpManager);
				if (obj instanceof MPopupMenu) {
					MPopupMenu popupMenu = (MPopupMenu) obj;
					if (popupMenu.getWidget() == null
							&& menuManager.getMenu() != null) {
						final MUIElement container = modelService
								.getContainer(popupMenu);
						if (container instanceof MPart) {
							MenuService.registerMenu(menuManager.getMenu()
									.getParent(), popupMenu,
									((MPart) container).getContext());
						}
					}
				}
			}
		}
