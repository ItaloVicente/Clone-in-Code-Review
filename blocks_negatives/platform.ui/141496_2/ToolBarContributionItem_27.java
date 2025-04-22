        if (checkDisposed()) {
            return -1;
        }
        return minimumItemsToShow;
    }

    /**
     * Returns the internal tool bar manager of the contribution item.
     *
     * @return the tool bar manager, or <code>null</code> if one is not
     *         defined.
     * @see IToolBarManager
     */
    @Override
