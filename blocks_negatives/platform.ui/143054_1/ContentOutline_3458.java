        return null;
    }

    /**
     * Returns the editor which contributed the current
     * page to this view.
     *
     * @return the editor which contributed the current page
     * or <code>null</code> if no editor contributed the current page
     */
    private IWorkbenchPart getContributingEditor() {
        return getCurrentContributingPart();
    }

    @Override
