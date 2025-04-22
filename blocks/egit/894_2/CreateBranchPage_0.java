		if (myBaseBranch != null
				&& myBaseBranch.getName().startsWith(Constants.R_REMOTES)) {
			nameText.setText(myBaseBranch.getName().substring(
					myBaseBranch.getName().lastIndexOf('/') + 1));
			checkPage();
		} else {
			setPageComplete(false);
		}
