			boolean layoutChanged = false;

			GridData gd = (GridData) warningComposite.getLayoutData();
			if (gd.exclude != !branchCombo.getText().startsWith(Constants.R_HEADS)) {
				gd.exclude = !branchCombo.getText().startsWith(Constants.R_HEADS);
				warningComposite.setVisible(!gd.exclude);
				layoutChanged = true;
			}

			warningComposite.getParent().getParent().layout(true);

			boolean showRebase = !branchCombo.getText().startsWith(Constants.R_TAGS) && !ObjectId.isId(branchCombo.getText());
			gd = (GridData) upstreamConfigGroup.getLayoutData();
			if (gd.exclude == showRebase) {
				gd.exclude = !showRebase;
				upstreamConfigGroup.setVisible(!gd.exclude);
				layoutChanged = true;
