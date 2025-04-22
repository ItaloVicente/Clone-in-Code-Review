			IContributionItem refItem = findInsertionPoint(IWorkbenchActionConstants.MB_ADDITIONS, actionSetId, mgr,
					true);
			ActionSetSeparator group = new ActionSetSeparator(name, actionSetId);
			if (refItem == null) {
				mgr.add(group);
			} else {
				mgr.insertAfter(refItem.getId(), group);
			}
		}

		public void contribute(IActionBars bars, boolean menuAppendIfMissing, boolean toolAppendIfMissing) {

			IMenuManager menuMgr = bars.getMenuManager();
			IToolBarManager toolBarMgr = bars.getToolBarManager();
			if (menus != null && menuMgr != null) {
				for (int i = 0; i < menus.size(); i++) {
					IConfigurationElement menuElement = (IConfigurationElement) menus.get(i);
					contributeMenu(menuElement, menuMgr, menuAppendIfMissing);
				}
			}

			if (actions != null) {
				for (int i = 0; i < actions.size(); i++) {
					ActionDescriptor ad = (ActionDescriptor) actions.get(i);
					if (menuMgr != null) {
