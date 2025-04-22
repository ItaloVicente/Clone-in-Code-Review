        return treeViewer.getSelection();
    }

    /**
     * Returns this page's tree viewer.
     *
     * @return this page's tree viewer, or <code>null</code> if
     *   <code>createControl</code> has not been called yet
     */
    protected TreeViewer getTreeViewer() {
        return treeViewer;
    }

    @Override
