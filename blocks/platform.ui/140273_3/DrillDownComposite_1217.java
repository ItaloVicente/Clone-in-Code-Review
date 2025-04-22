		toolBarMgr = new ToolBarManager(SWT.FLAT);
		ToolBar toolBar = toolBarMgr.createControl(this);
		gid = new GridData();
		gid.horizontalAlignment = GridData.FILL;
		gid.verticalAlignment = GridData.BEGINNING;
		toolBar.setLayoutData(gid);
	}
