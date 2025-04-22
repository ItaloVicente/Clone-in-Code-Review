        menuMgr.addMenuListener(new IMenuListener() {
            @Override
			public void menuAboutToShow(IMenuManager manager) {
                TaskList.this.fillContextMenu(manager);
            }
        });
