		if (result.getStatus() == Status.STOPPED)
			return createStoppedDialogArea(parent);
		if (result.getStatus() == Status.FAILED)
			return createFailedDialog(parent);
		createToggleButton(parent);
		return null;
	}

	private Control createFailedDialog(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		Label resultLabel = new Label(composite, SWT.NONE);
		resultLabel.setText(UIText.MergeResultDialog_result);
		resultLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false));
		Text resultText = new Text(composite, SWT.READ_ONLY);
		resultText.setText(result.getStatus().toString());
		resultText.setSelection(resultText.getCaretPosition());
		resultText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		if (result.getStatus() == Status.FAILED) {
			resultText.setForeground(parent.getDisplay().getSystemColor(
					SWT.COLOR_RED));

			StringBuilder paths = new StringBuilder();
			Label pathsLabel = new Label(composite, SWT.NONE);
			pathsLabel.setText(UIText.MergeResultDialog_failed);
			pathsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
					false));
			Text pathsText = new Text(composite, SWT.READ_ONLY);
			pathsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
					false));
			Set<Entry<String, MergeFailureReason>> failedPaths = result
					.getFailingPaths().entrySet();
			int n = 0;
			for (Map.Entry<String, MergeFailureReason> e : failedPaths) {
				if (n > 0)
					paths.append(Text.DELIMITER);
				paths.append(e.getValue());
				paths.append("\t"); //$NON-NLS-1$
				paths.append(e.getKey());
				n++;
				if (n > 10 && failedPaths.size() > 15)
					break;
			}
			if (n < failedPaths.size()) {
				paths.append(Text.DELIMITER);
				paths.append(MessageFormat.format(
						UIText.MergeResultDialog_nMore,
						Integer.valueOf(n - failedPaths.size())));
			}
			pathsText.setText(paths.toString());
