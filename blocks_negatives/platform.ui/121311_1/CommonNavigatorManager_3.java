		menuMgr.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
