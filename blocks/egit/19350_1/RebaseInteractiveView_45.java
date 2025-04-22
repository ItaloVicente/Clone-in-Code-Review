		startItem = new ToolItem(toolBar, SWT.NONE);
		startItem.setImage(UIIcons.getImage(resources,
				UIIcons.REBASE_PROCESS_STEPS));
		startItem.addSelectionListener(new RebaseCommandItemSelectionListener(
				new ProcessStepsRebaseCommand()));
		startItem.setEnabled(false);
		startItem.setText(UIText.InteractiveRebaseView_startItem_text);
