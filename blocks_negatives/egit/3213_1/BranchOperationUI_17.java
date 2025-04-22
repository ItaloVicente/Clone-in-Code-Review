	private void showResultDialog() {
		String displayedRef = createDisplayedRef();
		BranchResultDialog.show(bop.getResult(), repository, displayedRef);
		try {
			if (ObjectId.isId(repository.getFullBranch()) && bop.getResult().getStatus() == CheckoutResult.Status.OK)
				showDetachedHeadWarning();
		} catch (IOException e) {
		}
