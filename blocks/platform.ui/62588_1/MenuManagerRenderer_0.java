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
