
		MMenu mainMenu = application.getChildren().get(0).getMainMenu();
		assertNotNull(mainMenu);
		MMenu menu1 = (MMenu) findByElementId(mainMenu.getChildren(), "fragment.contributedMenu1");
		assertNotNull(menu1);
		MMenu menu2 = (MMenu) findByElementId(mainMenu.getChildren(), "fragment.contributedMenu2");
		assertNotNull(menu2);
		assertNotNull(findByElementId(menu1.getChildren(), "fragment.contributedMenuItem.csv"));
		assertNotNull(findByElementId(menu2.getChildren(), "fragment.contributedMenuItem.csv"));
		assertNotNull(findByElementId(menu1.getChildren(), "fragment.contributedMenuItem.xpath"));
		assertNotNull(findByElementId(menu2.getChildren(), "fragment.contributedMenuItem.xpath"));
	}

	private Object findByElementId(List<MMenuElement> children, String id) {
		for (MMenuElement item : children) {
			if (id.equals(item.getElementId())) {
				return item;
			}
		}
		return null;
