	private static class ContentAssistAction extends Action {

		private final SourceViewer viewer;

		public ContentAssistAction(SourceViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public void run() {
			if (viewer.canDoOperation(ISourceViewer.CONTENTASSIST_PROPOSALS)
					&& viewer.getTextWidget().isFocusControl()) {
				viewer.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
