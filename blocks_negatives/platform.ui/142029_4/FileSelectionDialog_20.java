        setResult(list);
        super.okPressed();
    }

    /**
     * Set whether the tree view of the file system element
     * will be fully expanded when the dialog is opened.
     *
     * @param expandAll true to expand all on dialog open, false otherwise.
     */
    public void setExpandAllOnOpen(boolean expandAll) {
        expandAllOnOpen = expandAll;
    }
