		final SubMenuManager quickFixMenu = new SubMenuManager(contextMenu);
		quickFixMenu.setVisible(true);
		quickFixMenu.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				quickFixMenu.removeAll();
				addProposals(quickFixMenu);
			}
		});
