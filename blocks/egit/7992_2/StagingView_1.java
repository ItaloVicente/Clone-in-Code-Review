	private static class IgnoreAction extends Action {

		private final IStructuredSelection selection;

		IgnoreAction(IStructuredSelection selection) {
			super(UIText.StagingView_IgnoreItemMenuLabel);
			this.selection = selection;
		}

		@Override
		public void run() {
			IgnoreOperationUI operation = new IgnoreOperationUI(
					getSelectedPaths(selection));
			operation.run();
		}
	}

