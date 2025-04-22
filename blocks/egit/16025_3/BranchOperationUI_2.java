	private void askForTargetIfNecessary() {
		if (target == null)
			target = getTargetWithDialog();
		if (target != null && showQuestionAboutTarget) {
			if (shouldShowCheckoutRemoteTrackingDialog(target))
				target = getTargetWithCheckoutRemoteTrackingDialog();
		}
	}

	private static boolean shouldShowCheckoutRemoteTrackingDialog(String refName) {
		boolean isRemoteTrackingBranch = refName != null
				&& refName.startsWith(Constants.R_REMOTES);
		if (isRemoteTrackingBranch) {
			boolean showDetachedHeadWarning = Activator.getDefault()
					.getPreferenceStore()
					.getBoolean(UIPreferences.SHOW_DETACHED_HEAD_WARNING);
			return showDetachedHeadWarning;
		} else {
			return false;
		}
	}

