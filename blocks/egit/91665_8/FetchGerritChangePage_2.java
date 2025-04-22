			private int getTotalWork(final CheckoutMode m,
					boolean checkoutNewBranch) {
				switch (m) {
				case CHECKOUT_FETCH_HEAD:
				case CREATE_TAG:
					return 2;
				case CREATE_BRANCH:
					return checkoutNewBranch ? 2 : 1;
				default:
					return 1;
