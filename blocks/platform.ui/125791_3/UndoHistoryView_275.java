		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				UndoHistoryView.this.fillContextMenu(manager);
			}
		});
