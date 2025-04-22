
		Point size = textContent.getTextWidget().computeSize(SWT.DEFAULT,
				SWT.DEFAULT);
		int yHint = size.y > 80 ? 80 : SWT.DEFAULT;
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, yHint).minSize(1, 20)
				.grab(true, true).applyTo(textContent);
