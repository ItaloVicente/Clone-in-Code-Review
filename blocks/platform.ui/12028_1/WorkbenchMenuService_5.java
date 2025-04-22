		MenuManagerRenderer renderer = (MenuManagerRenderer) obj;
		MMenu mMenu = renderer.getMenuModel(menuManager);
		if (mMenu != null) {
			final String tag;
			if ("popup".equals(location.getScheme())) { //$NON-NLS-1$
				tag = "popup:" + location.getPath(); //$NON-NLS-1$
			} else {
				tag = "menu:" + location.getPath(); //$NON-NLS-1$
