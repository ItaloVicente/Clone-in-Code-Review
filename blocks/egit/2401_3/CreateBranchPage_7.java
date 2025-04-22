			GridData gd = (GridData) warningComposite.getLayoutData();
			gd.exclude = branchCombo.getText().startsWith(Constants.R_REMOTES);
			warningComposite.setVisible(!gd.exclude);

			gd = (GridData) upstreamConfigGroup.getLayoutData();
			gd.exclude = branchCombo.getText().startsWith(Constants.R_TAGS);
			upstreamConfigGroup.setVisible(!gd.exclude);

			upstreamConfigGroup.getParent().layout(true);

			if (!gd.exclude)
				buttonConfigMerge.setSelection(false);
			buttonConfigRebase.setSelection(false);
			buttonConfigNone.setSelection(false);
			switch (getDefaultUpstreamConfig(myRepository, branchCombo
					.getText())) {
			case MERGE:
				buttonConfigMerge.setSelection(true);
				break;
			case REBASE:
				buttonConfigRebase.setSelection(true);
				break;
			case NONE:
				buttonConfigNone.setSelection(true);
				break;
			}

