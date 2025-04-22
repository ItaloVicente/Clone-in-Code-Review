	private CheckoutMode getCheckoutMode() {
		if (changeBranch.getSelection()) {
			return CheckoutMode.CHANGE_BRANCH;
		} else if (createBranch.getSelection()) {
			return CheckoutMode.CREATE_BRANCH;
		} else if (createTag.getSelection()) {
			return CheckoutMode.CREATE_TAG;
		} else if (checkoutFetchHead.getSelection()) {
			return CheckoutMode.CHECKOUT_FETCH_HEAD;
		} else {
			return CheckoutMode.NOCHECKOUT;
