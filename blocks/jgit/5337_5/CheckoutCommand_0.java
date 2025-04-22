			if (dco != null) {
				if (!dco.getToBeDeleted().isEmpty()) {
					status = new CheckoutResult(Status.NONDELETED
							dco.getToBeDeleted());
				} else
					status = new CheckoutResult(new ArrayList<String>(dco
							.getUpdated().keySet())
			} else
				status = new CheckoutResult(Status.OK
