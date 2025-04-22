		exp=0;
		hasExp=false;
	}
	
	public BaseGetOpImpl(String c, int e, GetlOperation.Callback cb,
			String k) {
		super(cb);
		cmd=c;
		keys=Collections.singleton(k);
		exp=e;
		hasExp=true;
