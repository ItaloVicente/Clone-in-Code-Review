		Label nameLabel = new Label(main, SWT.NONE);
		nameLabel.setText(UIText.CreateBranchPage_BranchNameLabel);

		Text prefix = new Text(main, SWT.NONE);
		prefix.setText(Constants.R_HEADS);
		prefix.setEnabled(false);

		nameText = new Text(main, SWT.BORDER);
		nameLabel.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent e) {
				nameText.setFocus();
			}
		});

