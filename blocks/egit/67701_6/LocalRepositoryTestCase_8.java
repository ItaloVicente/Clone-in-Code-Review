	protected static void closeGitViews() {
		for (String viewId : VIEWS_TO_CLOSE) {
			TestUtil.hideView(viewId);
		}
	}

