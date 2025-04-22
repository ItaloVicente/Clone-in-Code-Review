			private int getTotalWork(final CheckoutMode m) {
				switch (m) {
				case CHECKOUT_FETCH_HEAD:
				case CREATE_BRANCH:
					return 2;
				case CREATE_TAG:
					return 3;
				default:
					return 1;
