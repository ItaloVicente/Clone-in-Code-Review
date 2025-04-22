			fileToolBar.add(new GroupMarker(IWorkbenchActionConstants.SAVE_EXT));

			IContributionItem printItem = getPrintItem();
			fileToolBar.add(printItem);
			printItem.setVisible(false);
			fileToolBar.add(new GroupMarker(IWorkbenchActionConstants.PRINT_EXT));

			fileToolBar.add(new Separator(IWorkbenchActionConstants.BUILD_GROUP));
			fileToolBar.add(new GroupMarker(IWorkbenchActionConstants.BUILD_EXT));
			fileToolBar.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
