	@Override
	public void run(TestElement element) {
		TestElement child = element.getFirstChild();
		if (child != null)
			child.setLabel(child.getLabel() + " renamed child");
	}
