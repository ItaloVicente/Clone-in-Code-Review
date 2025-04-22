	private static class QuickfixAction extends Action {

		private final ITextOperationTarget textOperationTarget;

		public QuickfixAction(ITextOperationTarget target) {
			textOperationTarget = target;
		}

		@Override
		public void run() {
			textOperationTarget.doOperation(ISourceViewer.QUICK_ASSIST);
		}

	}

