		RowData rd = (RowData) menuTB.getLayoutData();
		if (needsMenu) {
			menuTB.getItem(0).setData(THE_PART_KEY, part);
			rd.exclude = false;
			menuTB.setVisible(true);
		} else {
			menuTB.getItem(0).setData(THE_PART_KEY, null);
			rd.exclude = true;
			menuTB.setVisible(false);
		}
