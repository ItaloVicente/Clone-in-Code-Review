        return viewer;
    }

    /**
     * Returns the tree viewer which shows the resource hierarchy.
     * @return the tree viewer
     * @since 2.0
     */
    public TreeViewer getTreeViewer() {
        return viewer;
    }

    /**
     * Returns the shell to use for opening dialogs.
     * Used in this class, and in the actions.
     *
     * @return the shell
     * @deprecated use getViewSite().getShell()
     */
    @Deprecated
