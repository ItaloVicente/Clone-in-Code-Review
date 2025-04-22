        return getParentMenuManager().getId();
    }

    /**
     * @return the parent menu manager that this sub-manager contributes to.
     */
    protected final IMenuManager getParentMenuManager() {
        return (IMenuManager) getParent();
    }

    @Override
