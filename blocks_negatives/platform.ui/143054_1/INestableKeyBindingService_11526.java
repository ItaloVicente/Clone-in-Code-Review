    /**
     * Marks the service associated with <code>nestedSite</code> as active if
     * one exists.  If there is no service associated, then nothing changes.
     * Calling this method with <code>null</code> forces deactivation of the
     * current service.
     *
     * @param nestedSite The site whose service should be activated;
     * <code>null</code> if the current service should be deactivated.
     * @return <code>true</code> if a service is activated (or deactivated, in
     * the case of a <code>null</code> parameter); <code>false</code> if
     * nothing changed.
     */
    public boolean activateKeyBindingService(IWorkbenchSite nestedSite);
