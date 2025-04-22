        mgr.addMenuListener(new IMenuListener() {
            @Override
			public void menuAboutToShow(IMenuManager mgr) {
                fillContextMenu(mgr);
            }
        });
