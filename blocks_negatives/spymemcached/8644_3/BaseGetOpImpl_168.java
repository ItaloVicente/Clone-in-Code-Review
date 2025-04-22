	public BaseGetOpImpl(String c,
			OperationCallback cb, Collection<String> k) {
		super(cb);
		cmd=c;
		keys=k;
		exp=0;
		hasExp=false;
		hasValue = false;
	}
