		initDragAndDrop();
		makeActions();

		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.add(copyAction);
		menuMgr.add(new Separator());
		menuMgr.add(defaultsAction);
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);

		viewer.getControl().addHelpListener(new HelpListener() {
			@Override
