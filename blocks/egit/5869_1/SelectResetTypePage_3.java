		Composite displayArea = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(displayArea);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(displayArea);

		RevCommit currentCommit = getLatestCommit(current);

		Label currentLabel = new Label(displayArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER)
				.applyTo(currentLabel);
		currentLabel.setText(UIText.SelectResetTypePage_labelCurrentHead);
		currentLabel.setToolTipText(UIText.SelectResetTypePage_tooltipCurrentHead);

		Composite currentArea = new Composite(displayArea, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(currentArea);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(currentArea);

		CLabel currentValue = new CLabel(currentArea, SWT.NONE);
		GridDataFactory.swtDefaults().applyTo(currentValue);
		Image currentIcon = getIcon(current);
		UIUtils.hookDisposal(currentValue, currentIcon);
		currentValue.setImage(currentIcon);
		currentValue.setText(Repository.shortenRefName(current));

		if (currentCommit != null) {
			CLabel commitLabel = new CLabel(currentArea, SWT.NONE);
			Image commitIcon = UIIcons.CHANGESET.createImage();
			UIUtils.hookDisposal(commitLabel, commitIcon);
			commitLabel.setImage(commitIcon);
			commitLabel.setText(currentCommit.abbreviate(7).name() + ":  " //$NON-NLS-1$
					+ currentCommit.getShortMessage());
			if (isCommit(current))
				((GridData) currentValue.getLayoutData()).exclude = true;
		}

		RevCommit targetCommit = getLatestCommit(target);

		Label targetLabel = new Label(displayArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.END, SWT.CENTER)
				.applyTo(targetLabel);
		targetLabel.setText(UIText.SelectResetTypePage_labelResettingTo);
		targetLabel.setToolTipText(UIText.SelectResetTypePage_tooltipResettingTo);

		Composite targetArea = new Composite(displayArea, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(targetArea);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(targetArea);

		CLabel targetValue = new CLabel(targetArea, SWT.NONE);
		GridDataFactory.swtDefaults().applyTo(targetValue);
		Image targetIcon = getIcon(target);
		UIUtils.hookDisposal(targetValue, targetIcon);
		targetValue.setImage(targetIcon);
		targetValue.setText(Repository.shortenRefName(target));

		if (targetCommit != null) {
			CLabel commitLabel = new CLabel(targetArea, SWT.NONE);
			Image commitIcon = UIIcons.CHANGESET.createImage();
			UIUtils.hookDisposal(commitLabel, commitIcon);
			commitLabel.setImage(commitIcon);
			commitLabel.setText(targetCommit.abbreviate(7).name() + ":  " //$NON-NLS-1$
					+ targetCommit.getShortMessage());
			if (isCommit(target))
				((GridData) targetValue.getLayoutData()).exclude = true;
		} else
			GridDataFactory.swtDefaults().span(2, 1).applyTo(targetLabel);

		Group g = new Group(displayArea, SWT.NONE);
