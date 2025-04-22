		Composite displayArea = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(displayArea);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(displayArea);

		RevCommit currentCommit = getLatestCommit(current);

		Label currentLabel = new Label(displayArea, SWT.NONE);
		currentLabel.setText(UIText.SelectResetTypePage_labelCurrentHead);
		currentLabel
				.setToolTipText(UIText.SelectResetTypePage_tooltipCurrentHead);

		CLabel currentValue = new CLabel(displayArea, SWT.NONE);
		GridDataFactory.swtDefaults().applyTo(currentValue);
		Image currentIcon = getIcon(current);
		UIUtils.hookDisposal(currentValue, currentIcon);
		currentValue.setImage(currentIcon);
		currentValue.setText(Repository.shortenRefName(current));

		if (currentCommit != null) {
			if (isCommit(current))
				currentValue.setText(formatCommit(currentCommit));
			else {
				new Label(displayArea, SWT.NONE);
				CLabel commitLabel = new CLabel(displayArea, SWT.NONE);
				Image commitIcon = UIIcons.CHANGESET.createImage();
				UIUtils.hookDisposal(commitLabel, commitIcon);
				commitLabel.setImage(commitIcon);
				commitLabel.setText(formatCommit(currentCommit));
			}
		}

		RevCommit targetCommit = getLatestCommit(target);

		Label targetLabel = new Label(displayArea, SWT.NONE);
		targetLabel.setText(UIText.SelectResetTypePage_labelResettingTo);
		targetLabel
				.setToolTipText(UIText.SelectResetTypePage_tooltipResettingTo);

		CLabel targetValue = new CLabel(displayArea, SWT.NONE);
		Image targetIcon = getIcon(target);
		UIUtils.hookDisposal(targetValue, targetIcon);
		targetValue.setImage(targetIcon);
		targetValue.setText(Repository.shortenRefName(target));

		if (targetCommit != null) {
			if (isCommit(target))
				targetValue.setText(formatCommit(targetCommit));
			else {
				new Label(displayArea, SWT.NONE);
				CLabel commitLabel = new CLabel(displayArea, SWT.NONE);
				Image commitIcon = UIIcons.CHANGESET.createImage();
				UIUtils.hookDisposal(commitLabel, commitIcon);
				commitLabel.setImage(commitIcon);
				commitLabel.setText(formatCommit(targetCommit));
			}
		}

		Group g = new Group(displayArea, SWT.NONE);
