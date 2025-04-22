			blobId = w.getObjectId(0);
			final EolStreamType eolStreamType = w
					.getEolStreamType(TreeWalk.OperationType.CHECKOUT_OP);
			final String filterCommand = w
					.getFilterCommand(Constants.ATTR_FILTER_TYPE_SMUDGE);
			metadata = new CheckoutMetadata(eolStreamType, filterCommand);
