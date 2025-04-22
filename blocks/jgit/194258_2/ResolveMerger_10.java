		EolStreamType streamType = EolStreamTypeUtil.detectStreamType(
				OperationType.CHECKOUT_OP
				attributes);
		try (OutputStream os = EolStreamTypeUtil.wrapOutputStream(
				new BufferedOutputStream(new FileOutputStream(of))
				streamType)) {
			rawMerged.writeTo(os
		}
