	}

	private IntermediateNode[] createModel() {
		IntermediateNode[] elements = new IntermediateNode[10];

		for (int i = 0; i < 10; i++) {
			elements[i] = new IntermediateNode(i);
			elements[i].generateChildren(1000);
