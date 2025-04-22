				status = new CheckoutResult(Status.NONDELETED
						dco.getToBeDeleted());
			} else
				status = new CheckoutResult(new ArrayList<String>(dco
						.getUpdated().keySet())

