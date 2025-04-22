		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				JobInfo info = getSelectedInfo();
				if (info == null) {
					return;
				}
