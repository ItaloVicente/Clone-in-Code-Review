	private void optimizeGets() {
		optimizedOp=writeQ.remove();
		if(writeQ.peek() instanceof GetOperation) {
			OptimizedGetImpl og=new OptimizedGetImpl(
					(GetOperation)optimizedOp);
			optimizedOp=og;
