        openPage(desc, event.stateMask);
    }

    private void openPage(IPerspectiveDescriptor desc, int keyStateMask) {
        if (pageInput == null) {
			StatusUtil.handleStatus(PAGE_PROBLEMS_TITLE
					+ ": " + PAGE_PROBLEMS_MESSAGE, StatusManager.SHOW); //$NON-NLS-1$
