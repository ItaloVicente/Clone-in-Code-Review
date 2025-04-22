	public MutatorOperationImpl(Mutator m, String k, long b,
			long d, int e, OperationCallback cb) {
		super(m == Mutator.incr ? CMD_INCR : CMD_DECR, generateOpaque(), k, cb);
		assert d >= 0 : "Default value is below zero";
		mutator=m;
		by=b;
		exp=e;
		def=d;
	}
