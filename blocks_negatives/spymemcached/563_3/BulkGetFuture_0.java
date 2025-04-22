		throws InterruptedException,
		ExecutionException, TimeoutException {
		if(!latch.await(timeout, unit)) {
			Collection<Operation> timedoutOps = new HashSet<Operation>();
			for(Operation op : ops) {
				if(op.getState() != OperationState.COMPLETE) {
