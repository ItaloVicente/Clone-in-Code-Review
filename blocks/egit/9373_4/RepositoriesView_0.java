		manager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager m) {
				getNavigatorActionService().fillContextMenu(m);
			}
		});
		getSite().registerContextMenu(manager, getCommonViewer());
