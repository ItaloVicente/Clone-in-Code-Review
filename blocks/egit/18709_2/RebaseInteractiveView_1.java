		skipItem = new ToolItem(toolBar, SWT.NONE);
		skipItem.setImage(UIIcons.getImage(resources, UIIcons.REBASE_SKIP));
		skipItem.addSelectionListener(new RebaseCommandItemSelectionListener(
				new SkipRebaseCommand()));
		skipItem.setText(UIText.InteractiveRebaseView_skipItem_text);
		skipItem.setEnabled(false);

		abortItem = new ToolItem(toolBar, SWT.NONE);
		abortItem.setImage(UIIcons.getImage(resources, UIIcons.REBASE_ABORT));
		abortItem.addSelectionListener(new RebaseCommandItemSelectionListener(
				new AbortRebaseCommand()));
		abortItem.setText(UIText.InteractiveRebaseView_abortItem_text);
		abortItem.setEnabled(false);
