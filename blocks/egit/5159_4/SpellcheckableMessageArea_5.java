				public void textChanged(TextEvent event) {
					if (undoAction != null)
						undoAction.update();
					if (redoAction != null)
						redoAction.update();
				}
			});
