			int flags = SWT.DROP_DOWN;
			if (index >= 0) {
				coolItem = new CoolItem(coolBar, flags, index);
			} else {
				coolItem = new CoolItem(coolBar, flags);
			}
			coolItem.setData(this);
			coolItem.setControl(toolBar);

			if (oldToolBar != toolBar) {
				toolBar.addListener(SWT.MenuDetect, event -> {
					if (toolBarManager.getContextMenuManager() == null) {
						handleContextMenu(event);
					}
