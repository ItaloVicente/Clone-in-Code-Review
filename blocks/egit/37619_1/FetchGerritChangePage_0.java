
	private static enum ActionAfterFetch {
		CreateBranch, CheckoutNewBranch, CreateTag, CheckoutFetchHead, UpdateFetchHead, ActivateAdditionalRefs;

		static EnumSet<ActionAfterFetch> fromSelection(Button checkout,
				Button createTag, Button createBranch,
				Button branchCheckoutButton, Button dontCheckout,
				Button activateAdditionalRefs) {
			EnumSet<ActionAfterFetch> result = EnumSet.noneOf(ActionAfterFetch.class);
			if (checkout.getSelection()) {
				result.add(CheckoutFetchHead);
			} else if (createTag.getSelection()) {
				result.add(CreateTag);
			} else if (createBranch.getSelection()) {
				result.add(CreateBranch);
			} else if (dontCheckout.getSelection()) {
				result.add(UpdateFetchHead);
			}
			if (branchCheckoutButton.getSelection()) {
				result.add(CheckoutNewBranch);
			}
			if ((checkout.getSelection() || dontCheckout.getSelection())
					&& activateAdditionalRefs.getSelection()) {
				result.add(ActivateAdditionalRefs);
			}
			return result;
		}
	}
