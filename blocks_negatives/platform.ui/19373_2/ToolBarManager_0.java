		if (!toolBarExist() && parent != null) {
			toolBar = new ToolBar(parent, itemStyle);
			toolBar.setMenu(getContextMenuControl());
			update(true);
			
			toolBar.getAccessible().addAccessibleListener(getAccessibleListener());
