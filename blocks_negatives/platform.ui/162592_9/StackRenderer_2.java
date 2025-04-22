			Composite trComp = (Composite) tabFolder.getTopRight();
			Control[] kids = trComp.getChildren();

			boolean needsTB = part != null && part.getToolbar() != null && part.getToolbar().isToBeRendered();

			MMenu viewMenu = getViewMenu(part);
			boolean needsMenu = viewMenu != null && hasVisibleMenuItems(viewMenu, part);

			ToolBar menuTB = (ToolBar) kids[kids.length - 1];

			RowData rd = (RowData) menuTB.getLayoutData();
			if (needsMenu) {
				menuTB.getItem(0).setData(THE_PART_KEY, part);
				menuTB.moveBelow(null);
				menuTB.pack();
				rd.exclude = false;
				menuTB.setVisible(true);
			} else {
				menuTB.getItem(0).setData(THE_PART_KEY, null);
				rd.exclude = true;
				menuTB.setVisible(false);
			}
