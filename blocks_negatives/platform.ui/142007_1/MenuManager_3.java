        return createMenuBar((Decorations) parent);
    }

    /**
     * Disposes of this menu manager and frees all allocated SWT resources. Notifies all
     * contribution items of the dispose. Note that this method does not clean up references between
     * this menu manager and its associated contribution items. Use {@link #removeAll()} for that
     * purpose, but note that will not dispose the items.
     */
    @Override
