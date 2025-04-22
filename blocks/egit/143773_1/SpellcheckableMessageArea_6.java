		sourceViewer.addSelectionChangedListener(event -> {
			if (standardActions[ITextOperationTarget.CUT] != null) {
				standardActions[ITextOperationTarget.CUT].update();
			}
			standardActions[ITextOperationTarget.COPY].update();
		});
