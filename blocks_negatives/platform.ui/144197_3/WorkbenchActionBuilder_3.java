			IToolBarManager helpToolBar = actionBarConfigurer.createToolBarManager();
			helpToolBar.add(new Separator(IWorkbenchActionConstants.GROUP_HELP));
			helpToolBar.add(new GroupMarker(IWorkbenchActionConstants.GROUP_APP));
			coolBar.add(actionBarConfigurer.createToolBarContributionItem(helpToolBar,
					IWorkbenchActionConstants.TOOLBAR_HELP));
		}
