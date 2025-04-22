		if (getWorkbenchWindow() == null) {
			return;
		}
		IWorkbenchPage page = getActivePage();
		if (page != null) {
			NavigationHistory nh = (NavigationHistory) page.getNavigationHistory();
			if (forward) {
				nh.forward();
			} else {
				nh.backward();
			}
			recreateMenu = true;
		}
	}
