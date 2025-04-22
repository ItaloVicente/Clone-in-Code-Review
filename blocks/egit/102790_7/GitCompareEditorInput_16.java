					if (metadata == null) {
						metadata = new CheckoutMetadata(
								tw.getEolStreamType(
										TreeWalk.OperationType.CHECKOUT_OP),
								tw.getFilterCommand(
										Constants.ATTR_FILTER_TYPE_SMUDGE));
					}
