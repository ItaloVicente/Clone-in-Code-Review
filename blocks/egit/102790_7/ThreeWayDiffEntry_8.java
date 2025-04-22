			if (!walk.isSubtree()) {
				e.metadata = new CheckoutMetadata(
						walk.getEolStreamType(
								TreeWalk.OperationType.CHECKOUT_OP),
						walk.getFilterCommand(
								Constants.ATTR_FILTER_TYPE_SMUDGE));
			}

