	public static AutoLFInputStream create(InputStream in
			StreamFlag... flags) {
		if (flags == null) {
			return new AutoLFInputStream(in
		}
		EnumSet<StreamFlag> set = EnumSet.noneOf(StreamFlag.class);
		set.addAll(Arrays.asList(flags));
		return new AutoLFInputStream(in
	}

	public AutoLFInputStream(InputStream in
		this.in = in;
		this.detectBinary = flags != null
				&& flags.contains(StreamFlag.DETECT_BINARY);
		this.abortIfBinary = flags != null
				&& flags.contains(StreamFlag.ABORT_IF_BINARY);
		this.forCheckout = flags != null
				&& flags.contains(StreamFlag.FOR_CHECKOUT);
	}

