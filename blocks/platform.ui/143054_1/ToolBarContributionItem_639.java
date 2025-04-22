		if (checkDisposed()) {
			return;
		}
		if (coolItem != null) {
			IToolBarManager manager = getToolBarManager();
			if (manager != null) {
				manager.update(true);
			}

			if ((propertyName == null) || propertyName.equals(ICoolBarManager.SIZE)) {
				updateSize(true);
			}
		}
	}

	private void updateSize(boolean changeCurrentSize) {
		if (checkDisposed()) {
			return;
		}
		if (coolItem == null || coolItem.isDisposed()) {
			return;
		}
		boolean locked = false;
		CoolBar coolBar = coolItem.getParent();
		try {
			if (coolBar != null) {
				if (coolBar.getLocked()) {
					coolBar.setLocked(false);
					locked = true;
				}
			}
			ToolBar toolBar = (ToolBar) coolItem.getControl();
			if ((toolBar == null) || (toolBar.isDisposed()) || (toolBar.getItemCount() <= 0)) {
				coolItem.setData(null);
				Control control = coolItem.getControl();
				if ((control != null) && !control.isDisposed()) {
					control.dispose();
					coolItem.setControl(null);
				}
				if (!coolItem.isDisposed()) {
					coolItem.dispose();
				}
			} else {
				Point toolBarSize = toolBar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				Point preferredSize = coolItem.computeSize(toolBarSize.x, toolBarSize.y);
				coolItem.setPreferredSize(preferredSize);
				if (getMinimumItemsToShow() != SHOW_ALL_ITEMS) {
					int toolItemWidth = toolBar.getItems()[0].getWidth();
					int minimumWidth = toolItemWidth * getMinimumItemsToShow();
					coolItem.setMinimumSize(minimumWidth, toolBarSize.y);
				} else {
					coolItem.setMinimumSize(toolBarSize.x, toolBarSize.y);
				}
				if (changeCurrentSize) {
					coolItem.setSize(preferredSize);
				}
			}
		} finally {
			if ((locked) && (coolBar != null)) {
				coolBar.setLocked(true);
			}
		}
	}
