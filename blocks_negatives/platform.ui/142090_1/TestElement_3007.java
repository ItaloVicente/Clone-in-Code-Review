        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
        }
    }

    static public TestElement createModel(int numLevels, int numChildren) {
        return new TestElement(new TestModel(numLevels, numChildren), null, 0,
                0);
    }

    public void deleteChild(TestElement element) {
        deleteChild(element, new TestModelChange(TestModelChange.REMOVE, this,
                element));
    }

    public void deleteChild(TestElement element, TestModelChange change) {
        basicDeleteChild(element);
        fModel.fireModelChanged(change);
    }

    public void deleteChildren() {
        for (int i = fChildren.size() - 1; i >= 0; i--) {
            TestElement te = fChildren.elementAt(i);
            fChildren.remove(te);
            te.fIsDeleted = true;
        }
        fModel.fireModelChanged(new TestModelChange(
                TestModelChange.STRUCTURE_CHANGE, this));
    }

    public void deleteSomeChildren() {
        for (int i = fChildren.size() - 1; i >= 0; i -= 2) {
            TestElement te = fChildren.elementAt(i);
            fChildren.remove(te);
            te.fIsDeleted = true;
        }
        fModel.fireModelChanged(new TestModelChange(
                TestModelChange.STRUCTURE_CHANGE, this));
    }

    @Override
