    protected void doStructureChange(TestModelChange change) {
        if (fViewer instanceof StructuredViewer) {
            ((StructuredViewer) fViewer).refresh(change.getParent());
        } else {
            Assert.isTrue(false, "Unknown kind of viewer");
        }
    }
