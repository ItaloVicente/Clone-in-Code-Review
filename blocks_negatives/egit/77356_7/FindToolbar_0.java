
		nextButton = new Button(this, SWT.PUSH);
		nextButton.setImage(nextIcon);
		nextButton.setText(UIText.HistoryPage_findbar_next);
		nextButton.setToolTipText(UIText.FindToolbar_NextTooltip);

		previousButton = new Button(this, SWT.PUSH);
		previousButton.setImage(previousIcon);
		previousButton.setText(UIText.HistoryPage_findbar_previous);
		previousButton.setToolTipText(UIText.FindToolbar_PreviousTooltip);

		final ToolBar toolBar = new ToolBar(this, SWT.FLAT);
		new ToolItem(toolBar, SWT.SEPARATOR);
