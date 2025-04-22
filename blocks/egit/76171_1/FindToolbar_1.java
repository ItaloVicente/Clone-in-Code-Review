		ToolBarManager manager = new ToolBarManager(SWT.HORIZONTAL);
		findNextAction = new Action() {
			@Override
			public void run() {
				findNext();
			}
		};
		findNextAction.setImageDescriptor(UIIcons.ELCL16_NEXT);
		findNextAction.setText(UIText.HistoryPage_findbar_next);
		findNextAction.setToolTipText(UIText.FindToolbar_NextTooltip);
		findNextAction.setEnabled(false);
		manager.add(findNextAction);
		findPreviousAction = new Action() {
			@Override
			public void run() {
				findPrevious();
			}
		};
		findPreviousAction.setImageDescriptor(UIIcons.ELCL16_PREVIOUS);
		findPreviousAction.setText(UIText.HistoryPage_findbar_previous);
		findPreviousAction.setToolTipText(UIText.FindToolbar_PreviousTooltip);
		findPreviousAction.setEnabled(false);
		manager.add(findPreviousAction);
		final ToolBar toolBar = manager.createControl(this);
