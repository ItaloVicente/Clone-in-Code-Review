        menuMgr.addMenuListener(new IMenuListener() {
            @Override
			public void menuAboutToShow(IMenuManager manager) {
                ResourceNavigator.this.fillContextMenu(manager);
            }
        });
