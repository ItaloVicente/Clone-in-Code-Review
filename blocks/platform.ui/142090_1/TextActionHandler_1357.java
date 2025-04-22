			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				activeTextControl.selectAll();
				return;
			}
			if (selectAllAction != null) {
				selectAllAction.runWithEvent(event);
				return;
			}
