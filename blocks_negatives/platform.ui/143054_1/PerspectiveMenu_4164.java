        return true;
    }

    /**
     * Runs an action for a particular perspective.  The behavior of the
     * action is defined by the subclass.
     *
     * @param desc the selected perspective
     */
    protected abstract void run(IPerspectiveDescriptor desc);

    /**
     * Runs an action for a particular perspective. The behavior of the action
     * is defined by the subclass. By default, this just calls
     * <code>run(IPerspectiveDescriptor)</code>.
     *
     * @param desc
     *            the selected perspective
     * @param event
     *            SelectionEvent - the event send along with the selection
     *            callback
     */
    protected void run(IPerspectiveDescriptor desc, SelectionEvent event) {
        run(desc);
    }
