		if (mergeResult.getMergeStatus() == MergeStatus.FAILED) {
			resultText.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_RED));

			StringBuilder paths = new StringBuilder();
			Label pathsLabel = new Label(composite, SWT.NONE);
			pathsLabel.setText(UIText.MergeResultDialog_failed);
			pathsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));
			Text pathsText = new Text(composite, SWT.READ_ONLY);
			pathsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
			Set<Entry<String, MergeFailureReason>> failedPaths = mergeResult.getFailingPaths().entrySet();
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
				paths.append(MessageFormat.format(UIText.MergeResultDialog_nMore, Integer.valueOf(n - failedPaths.size())));
			}
			pathsText.setText(paths.toString());
		}

		if (mergeResult.getMergeStatus() != MergeStatus.FAILED) {
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
		}

