	StatusLineContributionItem getStatusLine() {
		if (associatedWindow instanceof WorkbenchWindow) {
			WorkbenchWindow window = (WorkbenchWindow) associatedWindow;
			IStatusLineManager statusLine = window.getStatusLineManager();
			if (statusLine != null) { // this can be null if we're exiting
				IContributionItem item = statusLine.find("ModeContributionItem"); //$NON-NLS-1$
				if (item instanceof StatusLineContributionItem) {
					return ((StatusLineContributionItem) item);
				}
			}
		}
