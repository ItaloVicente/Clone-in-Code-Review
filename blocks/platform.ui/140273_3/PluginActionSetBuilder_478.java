	}

	private static class ActionSetContribution extends BasicContribution {
		private String actionSetId;

		private WorkbenchWindow window;

		protected ArrayList adjunctActions = new ArrayList(0);

		public ActionSetContribution(String id, IWorkbenchWindow window) {
			super();
			actionSetId = id;
			this.window = (WorkbenchWindow) window;
		}

		@Override
