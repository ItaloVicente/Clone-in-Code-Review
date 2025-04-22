			final ITextOperationTarget textOperationTarget) {
		Action proposalAction = new Action() {
			@Override
			public void run() {
				if (textOperationTarget
						.canDoOperation(ISourceViewer.CONTENTASSIST_PROPOSALS)
						&& getTextWidget().isFocusControl())
					textOperationTarget
							.doOperation(ISourceViewer.CONTENTASSIST_PROPOSALS);
			}
		};
