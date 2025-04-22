
			@Override
			public boolean belongsTo(Object family) {
				if (JobFamilies.FETCH.equals(family))
					return true;
				return super.belongsTo(family);
			}
		};
		job.setUser(true);
		job.schedule();
		return true;
	}

	private boolean showAdditionalRefs() {
		return (checkoutFetchHead.getSelection()
				|| cherryPickFetchHead.getSelection()
				|| updateFetchHead.getSelection())
				&& activateAdditionalRefs.getSelection();
	}

	private CheckoutMode getCheckoutMode() {
		if (createBranch.getSelection()) {
			return CheckoutMode.CREATE_BRANCH;
		} else if (createTag.getSelection()) {
			return CheckoutMode.CREATE_TAG;
		} else if (checkoutFetchHead.getSelection()) {
			return CheckoutMode.CHECKOUT_FETCH_HEAD;
		} else if (cherryPickFetchHead.getSelection()) {
			return CheckoutMode.CHERRY_PICK;
		} else {
			return CheckoutMode.NOCHECKOUT;
