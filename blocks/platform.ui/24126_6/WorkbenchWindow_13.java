			IMenuManager menuBarManager = getMenuBarManager();
			if (menuBarManager instanceof MenuManager) {
				MenuManagerRenderer mr = (MenuManagerRenderer) rendererFactory.getRenderer(
						modelService.createModelElement(MMenu.class), null);
				MMenu parent = mr.getMenuModel((MenuManager) menuBarManager);
				if (parent != null) {
					mr.reconcileManagerToModel((MenuManager) menuBarManager, parent);
				}
			}

