				menuUpdater = new Runnable() {
					@Override
					public void run() {
						try {
							if (model.getMainMenu() == null || model.getWidget() == null
									|| menu.isDisposed() || mainMenu.getWidget() == null) {
								return;
							}
							MenuManagerRendererFilter.updateElementVisibility(mainMenu, renderer,
									menuManager, windowContext.getActiveLeaf(), 1, false);
							menuManager.update(true);
						} finally {
							canUpdateMenus = true;
