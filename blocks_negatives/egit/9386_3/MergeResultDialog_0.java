		Label newHeadLabel = new Label(composite, SWT.NONE);
		newHeadLabel.setText(UIText.MergeResultDialog_newHead);
		newHeadLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false));
		Text newHeadText = new Text(composite, SWT.READ_ONLY);
		ObjectId newHead = mergeResult.getNewHead();
		if (newHead != null)
			newHeadText.setText(getCommitMessage(newHead) + SPACE
					+ abbreviate(mergeResult.getNewHead(), true));
		newHeadText
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
