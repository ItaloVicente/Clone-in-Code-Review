		return new BranchOperationUI(repository, target, true);
	}

	public static BranchOperationUI checkoutWithoutQuestion(
			Repository repository, String target) {
		return new BranchOperationUI(repository, target, false);
	}

	public static boolean checkoutWillShowQuestionDialog(String refName) {
		return shouldShowCheckoutRemoteTrackingDialog(refName);
