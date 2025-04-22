			try {
				dco.checkout();
			} catch (CheckoutConflictException e) {
				status = new CheckoutResult(Status.CONFLICTS
						.getConflicts());
				throw e;
			}
