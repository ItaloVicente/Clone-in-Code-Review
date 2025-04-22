	public void fill(MenuManagerRenderer renderer, MMenu menu, IMenuManager manager) {
		for (IContributionItem item : manager.getItems()) {
			if (item instanceof MenuManager) {
				MenuManager menuManager = (MenuManager) item;
				MMenu subMenu = MenuHelper.createMenu(menuManager);
				if (subMenu != null) {
					renderer.linkModelToContribution(subMenu, item);
					renderer.linkModelToManager(subMenu, menuManager);
					fill(renderer, subMenu, menuManager);
					menu.getChildren().add(subMenu);
				}
			} else if (item instanceof AbstractGroupMarker) {
				MMenuSeparator separator = modelService.createModelElement(MMenuSeparator.class);
				separator.setVisible(item.isVisible());
				separator.setElementId(item.getId());
				if (item instanceof GroupMarker) {
					separator.getTags().add(MenuManagerRenderer.GROUP_MARKER);
				}
				menu.getChildren().add(separator);
				manager.remove(item);
			} else {
				MMenuItem menuItem = OpaqueElementUtil.createOpaqueMenuItem();
				menuItem.setElementId(item.getId());
				menuItem.setVisible(item.isVisible());
				OpaqueElementUtil.setOpaqueItem(menuItem, item);
				menu.getChildren().add(menuItem);
				renderer.linkModelToContribution(menuItem, item);
			}
		}
	}

