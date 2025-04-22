			IToolBarManager navToolBar = actionBarConfigurer.createToolBarManager();
			navToolBar.add(new Separator(
					IWorkbenchActionConstants.HISTORY_GROUP));
			navToolBar
					.add(new GroupMarker(IWorkbenchActionConstants.GROUP_APP));
			navToolBar.add(backwardHistoryAction);
			navToolBar.add(forwardHistoryAction);
			navToolBar.add(new Separator(IWorkbenchActionConstants.PIN_GROUP));
			navToolBar.add(getPinEditorItem());

			coolBar.add(actionBarConfigurer.createToolBarContributionItem(navToolBar,
					IWorkbenchActionConstants.TOOLBAR_NAVIGATE));
		}
