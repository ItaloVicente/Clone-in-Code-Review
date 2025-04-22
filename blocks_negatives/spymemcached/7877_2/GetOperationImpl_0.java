		exp=0;
	}

	public GetOperationImpl(String k, GetsOperation.Callback cb) {
		super(GET_CMD, generateOpaque(), k, cb);
		exp=0;
	}

	public GetOperationImpl(String k, int e, GetlOperation.Callback cb) {
		super(GETL_CMD, generateOpaque(), k, cb);
		exp=e;
	}

	public GetOperationImpl(String k, int e, GetAndTouchOperation.Callback cb) {
		super(GAT_CMD, generateOpaque(), k, cb);
		exp=e;
