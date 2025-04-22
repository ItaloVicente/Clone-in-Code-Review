    }

    /**
     * Returns the menu manager containing the actions.
     */
    protected abstract MenuManager getActionMenuManager(ListView view)
            throws Throwable;

    /**
     * Tests the enablement / visibility of an action.
     */
    protected abstract void testAction(MenuManager mgr, String action,
            boolean expected) throws Throwable;
