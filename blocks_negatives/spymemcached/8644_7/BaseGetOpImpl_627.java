	public BaseGetOpImpl(String c, int e, OperationCallback cb,
			String k) {
		super(cb);
		cmd=c;
		keys=Collections.singleton(k);
		exp=e;
		hasExp=true;
		hasValue = false;
	}
