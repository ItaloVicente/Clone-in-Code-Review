		if (useNewLook)
			createClearTextNew(parent);
		else
			createClearTextOld(parent);
		if (clearButtonControl != null) {
			clearButtonControl.setVisible(false);
		}
		if (filterToolBar != null) {
			filterToolBar.update(false);
			filterToolBar.getControl().setVisible(false);
		}
