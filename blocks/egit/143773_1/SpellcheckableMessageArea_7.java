			sourceViewer.addTextListener(event -> {
				if (standardActions[ITextOperationTarget.UNDO] != null) {
					standardActions[ITextOperationTarget.UNDO].update();
				}
				if (standardActions[ITextOperationTarget.REDO] != null) {
					standardActions[ITextOperationTarget.REDO].update();
