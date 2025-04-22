		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				BoxView.this.fillContextMenu(manager);
			}
		});
