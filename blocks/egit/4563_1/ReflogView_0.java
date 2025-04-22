
		MenuManager menuManager = new MenuManager();
		menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		Tree tree = refLogTableTreeViewer.getTree();
		tree.setMenu(menuManager.createContextMenu(tree));
		getSite().registerContextMenu(POPUP_MENU_ID, menuManager, refLogTableTreeViewer);
