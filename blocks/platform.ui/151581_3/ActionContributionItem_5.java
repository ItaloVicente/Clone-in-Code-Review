		if (flags == SWT.CASCADE) {
			Menu subMenu = new Menu(parent);
			subMenu.addListener(SWT.Show, getMenuCreatorListener());
			subMenu.addListener(SWT.Hide, getMenuCreatorListener());
			mi.setMenu(subMenu);
		}
