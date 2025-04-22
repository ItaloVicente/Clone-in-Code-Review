			if (!paths.isEmpty()) {
				checkoutPaths();
				status = CheckoutResult.OK_RESULT;
				setCallable(false);
				return null;
			}
