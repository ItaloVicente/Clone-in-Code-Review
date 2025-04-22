        fModel.fireModelChanged(change);
    }

    public TestElement[] addChildren(int event) {
        TestElement elements[] = new TestElement[] {
                new TestElement(fModel, this), new TestElement(fModel, this) };

        elements[0].fSomeName = "added1";
        elements[1].fSomeName = "added2";
        elements[1].fId += "madeUnique";
        addChildren(elements, new TestModelChange(event, this, elements));
        return elements;
    }

    public TestElement basicAddChild() {
        TestElement element = new TestElement(fModel, this);
        element.fSomeName = "added";
        fChildren.add(element);
        return element;
    }

    public void basicDeleteChild(TestElement element) {
        fChildren.remove(element);
        element.fIsDeleted = true;
    }

    private int childId() {
