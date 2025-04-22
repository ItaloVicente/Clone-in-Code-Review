		super(GET_CMD, generateOpaque(), cb);
		key=k;
		exp=0;
		hasExp=false;
	}
	
	public GetOperationImpl(String k, int e, GetlOperation.Callback cb) {
		super(GETL_CMD, generateOpaque(), cb);
