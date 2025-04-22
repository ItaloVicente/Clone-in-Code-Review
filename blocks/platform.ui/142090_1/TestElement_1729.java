	public TestElement(TestModel model, TestElement container) {
		fModel = model;
		fContainer = container;
		int p = 0;
		TestElement lastSibling = container.getLastChild();
		if (lastSibling != null) {
			p = lastSibling.childId() + 1;
		}
		fId = container.getID() + "-" + p;
	}

	public TestElement(TestModel model, TestElement container, int level,
			int position) {
		fModel = model;
		fContainer = container;
		if (container != null) {
