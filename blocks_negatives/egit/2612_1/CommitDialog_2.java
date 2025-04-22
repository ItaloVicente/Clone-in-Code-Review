		commitText = new SpellcheckableMessageArea(container, commitMessage);
		Point size = commitText.getTextWidget().getSize();
		int minHeight = commitText.getTextWidget().getLineHeight() * 3;
		commitText.setLayoutData(GridDataFactory.fillDefaults().span(2, 1).grab(true, true)
				.hint(size).minSize(size.x, minHeight).align(SWT.FILL, SWT.FILL).create());
		commitText.setText(calculateCommitMessage());
