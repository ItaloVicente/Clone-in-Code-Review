			if (activeEditor != null) {
				activeEditor.performRedo();
				return;
			}
			if (redoAction != null) {
				redoAction.runWithEvent(event);
				return;
			}
