    protected void doRemove(TestModelChange change) {
        if (fViewer instanceof ListViewer) {
            ((ListViewer) fViewer).remove(change.getChildren());
        } else if (fViewer instanceof TableViewer) {
            ((TableViewer) fViewer).remove(change.getChildren());
        } else if (fViewer instanceof AbstractTreeViewer) {
            ((AbstractTreeViewer) fViewer).remove(change.getChildren());
        } else if (fViewer instanceof ComboViewer) {
            ((ComboViewer) fViewer).remove(change.getChildren());
        } else {
            Assert.isTrue(false, "Unknown kind of viewer");
        }
    }
