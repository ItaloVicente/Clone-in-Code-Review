	protected static void waitForWorkspaceRefresh() {
		WorkspaceRefreshHook wrh = new WorkspaceRefreshHook();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(wrh);

		try {
			bot.waitUntil(wrh, 120000);
		} finally {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(wrh);
		}
	}

	private static class WorkspaceRefreshHook extends DefaultCondition
			implements IResourceChangeListener {
		private boolean state = false;

		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() == IResourceChangeEvent.POST_CHANGE)
				state = true;
		}

		public String getFailureMessage() {
			return "Failed waiting for workspace refresh.";
		}

		public boolean test() throws Exception {
			return state;
		}
	}

