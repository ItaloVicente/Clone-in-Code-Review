		buildActions.put(window, buildAction);
		
		window.addPageListener(new IPageListener() {
			public void pageOpened(IWorkbenchPage page) {
			}
			public void pageClosed(IWorkbenchPage page) {
				IWorkbenchWindow closedWindow = page.getWorkbenchWindow();
				buildActions.remove(closedWindow);
				closedWindow.removePageListener(this);
			}
			public void pageActivated(IWorkbenchPage page) {
			}
		});
		
		return buildAction;
