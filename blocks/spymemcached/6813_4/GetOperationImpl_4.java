		cmd=GETL_CMD;
	}

	public GetOperationImpl(String k, int e, GetAndTouchOperation.Callback cb) {
		super(GAT_CMD, generateOpaque(), cb);
		key=k;
		exp=e;
		cmd=GAT_CMD;
