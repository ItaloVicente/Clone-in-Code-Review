    /**
     * Notifies any help listeners that help has been requested.
     * Only listeners registered at the time this method is called are notified.
     *
     * @param event a help event
     *
     * @see HelpListener#helpRequested(org.eclipse.swt.events.HelpEvent)
     */
    protected void fireHelpRequested(HelpEvent event) {
