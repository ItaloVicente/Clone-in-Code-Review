    /**
     * Runs this action, passing the triggering SWT event.
     * As of 2.0, <code>ActionContributionItem</code> calls this method
     * instead of <code>run()</code>.
     * The default implementation of this method in <code>Action</code>
     * simply calls <code>run()</code> for backwards compatibility.
     *
     * @param event the SWT event which triggered this action being run
     * @since 2.0
     *
     * @see #AS_RADIO_BUTTON How radio buttons are handled
     * @see #AS_CHECK_BOX How check boxes are handled
     */
