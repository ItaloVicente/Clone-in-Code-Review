        fSomeName = "name-" + position;

        if (level < model.getNumLevels()) {
            for (int i = 0; i < model.getNumChildren(); i++) {
                fChildren.add(new TestElement(model, this, level + 1, i));
            }
        }
    }

    public TestElement addChild(int event) {
        TestElement element = new TestElement(fModel, this);
        element.fSomeName = "added";
        addChild(element, new TestModelChange(event, this, element));
        return element;
    }

    public TestElement addChild(TestElement element, TestModelChange change) {
        fChildren.add(element);
        fModel.fireModelChanged(change);
        return element;
    }

    public void addChildren(TestElement[] elements, TestModelChange change) {
        for (TestElement element : elements) {
