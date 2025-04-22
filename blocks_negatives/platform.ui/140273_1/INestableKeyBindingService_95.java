    /**
     * An accessor for the nested key binding service associated with a
     * particular site.  If the key binding service does not exist for this
     * <code>nestedSite</code> already, then a new one should be constructed.
     *
     * @param nestedSite The site for which the service should be found;
     * should not be <code>null</code>.
     * @return The associated service, if any; or a new associated service, if
     * none existed previously.
     */
    IKeyBindingService getKeyBindingService(IWorkbenchSite nestedSite);
