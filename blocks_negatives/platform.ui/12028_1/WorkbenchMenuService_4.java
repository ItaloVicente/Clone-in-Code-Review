	
		MMenu mMenu = null;

		Menu menu = menuManager.getMenu();
		final MPart mPart = getPartToExtend(menu == null ? null : menu.getParent());
		if (mPart != null) {
			for (MMenu mMenuIt : mPart.getMenus()) {
				if (mMenuIt.getElementId().equals(menuManager.getId())) {
					mMenu = mMenuIt;
				}
