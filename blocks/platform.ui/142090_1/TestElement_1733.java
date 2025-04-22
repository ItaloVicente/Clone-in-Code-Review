		TestElement element = (TestElement) arg;
		return element.fId.equals(fId);
	}

	public TestElement getChildAt(int i) {
		return fChildren.elementAt(i);
	}

	public int getChildCount() {
		return fChildren.size();
	}

	public TestElement[] getChildren(){
		TestElement[] result = new TestElement[fChildren.size()];
		fChildren.toArray(result);
		return result;
	}

	public TestElement getContainer() {
		return fContainer;
	}

	public TestElement getFirstChild() {
		if (fChildren.size() > 0) {
