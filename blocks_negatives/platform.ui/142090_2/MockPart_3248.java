    /**
     *
     */
    public MockPart() {
        callTrace = new CallHistory(this);
        selectionProvider = new MockSelectionProvider();
    }
