					if (menuModel.getParent() == null) {
						if (menuModel instanceof MPopupMenu) {
							Object data = getUIContainer(menuModel);
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
