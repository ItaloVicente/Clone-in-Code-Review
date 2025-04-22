				MMenu childMenu = getMenuModel(childManager);
				if (childMenu == null) {
					childMenu = OpaqueElementUtil.createOpaqueMenu();
					childMenu.setElementId(item.getId());
					childMenu.setLabel(((MenuManager) item).getMenuText());
					OpaqueElementUtil.setOpaqueItem(childMenu, item);
					childMenu.setRenderer(this);
					linkModelToManager(childMenu, childManager);
