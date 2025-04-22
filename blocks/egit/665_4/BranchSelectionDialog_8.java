
	private boolean canConfirm() {
		String refName = refNameFromDialog();


		boolean canConfirm;
		if (showResetType)
			canConfirm = refName != null
					&& ((refName.startsWith(Constants.R_HEADS) || refName
							.startsWith(Constants.R_REMOTES)));
		else
			canConfirm = refName != null
					&& (refName.startsWith(Constants.R_HEADS));

		return canConfirm;
	}

	private boolean branchSelected() {
		String refName = refNameFromDialog();

		return refName != null
				&& ((refName.startsWith(Constants.R_HEADS) || refName
						.startsWith(Constants.R_REMOTES)));
	}
