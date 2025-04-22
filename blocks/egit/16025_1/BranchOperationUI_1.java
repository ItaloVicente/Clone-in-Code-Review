	private void askForTargetIfNecessary() {
		if (target == null)
			target = getTargetWithDialog();
		if (target != null) {
			boolean isRemoteBranch = target.startsWith(Constants.R_REMOTES);
			if (isRemoteBranch)
				target = getTargetAskHowToCheckoutRemoteTrackingBranch();
		}
	}

