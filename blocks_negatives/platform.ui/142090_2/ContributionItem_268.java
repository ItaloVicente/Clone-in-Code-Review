        return id;
    }

    /**
     * Returns the parent contribution manager, or <code>null</code> if this
     * contribution item is not currently added to a contribution manager.
     *
     * @return the parent contribution manager, or <code>null</code>
     * @since 2.0
     */
    public IContributionManager getParent() {
        return parent;
    }

    /**
     * The default implementation of this <code>IContributionItem</code>
     * method returns <code>false</code>. Subclasses may override.
     */
    @Override
