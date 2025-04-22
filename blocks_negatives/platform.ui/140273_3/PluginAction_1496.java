        return pluginId;
    }

    /**
     * Disposes the delegate, if created.
     *
     * @since 3.1
     */
    public void disposeDelegate() {
        if (getDelegate() instanceof IActionDelegate2) {
            ((IActionDelegate2) getDelegate()).dispose();
        }
        else if (getDelegate() instanceof IWorkbenchWindowActionDelegate) {
            ((IWorkbenchWindowActionDelegate) getDelegate()).dispose();
        }
        delegate = null;
    }

    /**
     * Disposes this plugin action.
     *
     * @since 3.1
     */
    public void dispose() {
        disposeDelegate();
        selection = null;
    }

    @Override
