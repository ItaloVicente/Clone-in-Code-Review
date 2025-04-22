	public static Dialog getDialog(Shell parentShell, Repository repository, MergeResult mergeResult) {
		if(mergeResult.getMergeStatus() == MergeStatus.CHECKOUT_CONFLICT)
			return new CheckoutConflictDialog(parentShell, repository, mergeResult.getCheckoutConflicts());
		else
			return new MergeResultDialog(parentShell, repository, mergeResult);
	}

