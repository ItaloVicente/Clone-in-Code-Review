		if (!menuItem.isToBeRendered()) {
			return null;
		}

		if (menuItem instanceof MMenu) {
			MenuManager manager = menuMngrRenderer.getManager((MMenu) menuItem);

			String text;
			ImageDescriptor iconDescriptor;
			if (OpaqueElementUtil.isOpaqueMenu(menuItem)) {
				text = manager.getMenuText();
				iconDescriptor = manager.getImageDescriptor();
