		
		String closeText = IDEWorkbenchMessages.Workbench_closePerspective;
		MenuManager closePerspMenuMgr = new MenuManager(closeText, "closePerspective"); //$NON-NLS-1$
		
		closePerspMenuMgr.add(closePerspAction);
		closePerspMenuMgr.add(closeAllPerspsAction);
		menu.add(closePerspMenuMgr);
		menu.add(getResetPerspectiveItem());
		
		
