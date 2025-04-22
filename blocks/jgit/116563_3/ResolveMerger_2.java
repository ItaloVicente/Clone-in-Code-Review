		}
		EolStreamType streamType = EolStreamTypeUtil.detectStreamType(
				OperationType.CHECKOUT_OP
				tw.getAttributes());
		try (OutputStream os = EolStreamTypeUtil.wrapOutputStream(
				new BufferedOutputStream(new FileOutputStream(of))
				streamType)) {
