			StringBuilder paths = new StringBuilder();
			Label pathsLabel = new Label(composite, SWT.NONE);
			pathsLabel.setText(UIText.MergeResultDialog_conflicts);
			pathsLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false,
					false));
			Text pathsText = new Text(composite, SWT.READ_ONLY);
			pathsText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
					false));
			List<String> conflList = result.getConflicts();
			int n = 0;
			for (String e : conflList) {
				if (n > 0)
					paths.append(Text.DELIMITER);
				paths.append(e);
				n++;
				if (n > 10 && conflList.size() > 15)
					break;
			}
			if (n < conflList.size()) {
				paths.append(Text.DELIMITER);
				paths.append(MessageFormat.format(
						UIText.MergeResultDialog_nMore,
						Integer.valueOf(n - conflList.size())));
			}
			pathsText.setText(paths.toString());
		}
