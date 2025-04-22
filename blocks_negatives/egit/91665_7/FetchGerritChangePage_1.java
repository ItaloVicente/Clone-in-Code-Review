		final boolean doCheckout = checkout.getSelection();
		final boolean doCreateTag = createTag.getSelection();
		final boolean doChangeBranch = changeBranch.getSelection();
		final boolean doCreateBranch = createBranch.getSelection();
		final boolean doCheckoutNewBranch = branchCheckoutButton.getSelection();
		final boolean doActivateAdditionalRefs = (checkout.getSelection() || dontCheckout
				.getSelection()) && activateAdditionalRefs.getSelection();
