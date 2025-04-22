			try {
				checkout.checkout();
			} catch (org.eclipse.jgit.errors.CheckoutConflictException cce) {
				throw new CheckoutConflictException(checkout.getConflicts()
						cce);
			}
