
		sourceViewer.addTextListener(new ITextListener() {

			public void textChanged(TextEvent event) {
				undoAction.update();
				redoAction.update();
			}
		});
