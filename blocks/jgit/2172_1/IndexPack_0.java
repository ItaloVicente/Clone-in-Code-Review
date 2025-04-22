	private void resolveDeltas(DeltaVisit initialDelta) throws IOException {
	Stack<DeltaVisit> stack = new Stack<DeltaVisit>();
	stack.push(initialDelta);
	while (!stack.isEmpty()) {
		DeltaVisit dv=stack.pop();

