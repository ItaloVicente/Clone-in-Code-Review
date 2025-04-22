			if (dco != null && !dco.getToBeDeleted().isEmpty()) {
				status = new CheckoutResult(Status.NONDELETED, dco
						.getToBeDeleted());
			}
			else
				status = CheckoutResult.OK_RESULT;
