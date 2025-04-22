	@Override
	protected void optimize() {
		Operation firstOp = writeQ.peek();
		if(firstOp instanceof GetOperation) {
			optimizeGets();
		} else if(firstOp instanceof CASOperation) {
			optimizeSets();
		}
	}
