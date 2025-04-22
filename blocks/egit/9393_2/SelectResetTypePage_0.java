		if (!current.equals(target)) {
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
