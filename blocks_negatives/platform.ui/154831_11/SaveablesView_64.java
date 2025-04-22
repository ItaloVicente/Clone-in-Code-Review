		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				SaveablesView.this.fillContextMenu(manager);
			}
		});
