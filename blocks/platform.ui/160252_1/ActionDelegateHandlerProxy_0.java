		if (viewDelegate != null) {
			IWorkbenchPage page = window.getActivePage();
			if (page != null) {
				IViewPart viewPart = page.findView(viewId);
				viewDelegate.init(viewPart);
			}
		}
