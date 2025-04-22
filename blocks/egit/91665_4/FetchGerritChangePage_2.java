			private int getTotalWork(final CheckoutMode m) {
				switch (m) {
				case CHECKOUT_FETCH_HEAD:
				case CREATE_TAG:
				case CREATE_BRANCH:
					return 2;
				default:
					return 1;
