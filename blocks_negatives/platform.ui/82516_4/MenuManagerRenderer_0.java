	private EventHandler toBeRenderedUpdater = new EventHandler() {
		@Override
		public void handleEvent(Event event) {
			Object element = event.getProperty(UIEvents.EventTags.ELEMENT);
			String attName = (String) event.getProperty(UIEvents.EventTags.ATTNAME);
			if (element instanceof MMenuItem) {
				MMenuItem itemModel = (MMenuItem) element;
				if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
					Object obj = itemModel.getParent();
					if (!(obj instanceof MMenu)) {
						return;
					}
					MenuManager parent = getManager((MMenu) obj);
					if (itemModel.isToBeRendered()) {
						if (parent != null) {
							modelProcessSwitch(parent, itemModel);
						}
					} else {
						IContributionItem ici = getContribution(itemModel);
						clearModelToContribution(itemModel, ici);
						if (ici != null && parent != null) {
							parent.remove(ici);
						}
						if (ici != null) {
							ici.dispose();
						}
					}
				}
			}

			if (element instanceof MPart) {
				MPart part = (MPart) element;
				if (UIEvents.UIElement.TOBERENDERED.equals(attName)) {
					boolean tbr = (Boolean) event.getProperty(UIEvents.EventTags.NEW_VALUE);
					if (!tbr) {
						List<MMenu> menus = part.getMenus();
						for (MMenu menu : menus) {
							if (menu instanceof MPopupMenu)
								unlinkMenu(menu);
						}
					}
				}
			}

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
							}
						}
					}
				} else if (element instanceof MMenuElement) {
					MMenuElement itemModel = (MMenuElement) element;
					Object obj = getContribution(itemModel);
					if (!(obj instanceof ContributionItem)) {
						return;
					}
					ContributionItem item = (ContributionItem) obj;
					item.setVisible(itemModel.isVisible());
					if (item.getParent() != null) {
						item.getParent().markDirty();
						scheduleManagerUpdate(item.getParent());
					}
				}
			}
		}
	};
