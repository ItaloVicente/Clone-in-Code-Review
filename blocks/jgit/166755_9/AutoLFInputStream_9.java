		this.forCheckout = false;
	}

	public AutoLFInputStream(InputStream in
		this.in = in;
		this.detectBinary = flags.contains(StreamFlag.DETECT_BINARY);
		this.abortIfBinary = flags.contains(StreamFlag.ABORT_IF_BINARY);
		this.forCheckout = flags.contains(StreamFlag.FOR_CHECKOUT);
