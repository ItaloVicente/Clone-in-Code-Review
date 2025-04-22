        menuMgr.addMenuListener(new IMenuListener() {
            @Override
			public void menuAboutToShow(IMenuManager manager) {
                AdaptedResourceNavigator.this.fillContextMenu(manager);
            }
        });
