		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				MenuContributionHarness.this.fillContextMenu(manager);
			}
		});
