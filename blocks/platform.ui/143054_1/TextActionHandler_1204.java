			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				activeTextControl.selectAll();
				updateActionsEnableState();
				return;
			}
			if (selectAllAction != null) {
				selectAllAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				setEnabled(activeTextControl.getCharCount() > 0);
				return;
			}
			if (selectAllAction != null) {
				setEnabled(selectAllAction.isEnabled());
				return;
			}
			setEnabled(false);
		}
	}

	public TextActionHandler(IActionBars actionBar) {
		super();
		actionBars = actionBar;
		updateActionBars();
	}

