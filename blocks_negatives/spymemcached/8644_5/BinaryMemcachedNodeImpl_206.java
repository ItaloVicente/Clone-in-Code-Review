	private void optimizeSets() {
		optimizedOp=writeQ.remove();
		if(writeQ.peek() instanceof CASOperation) {
			OptimizedSetImpl og=new OptimizedSetImpl(
					(CASOperation)optimizedOp);
			optimizedOp=og;
