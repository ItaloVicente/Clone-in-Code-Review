    /**
     * Removes a nested key binding service from this key binding service.  The
     * service to remove is determined by the <code>nestedSite</code> with
     * which it is associated.
     *
     * @param nestedSite The site from which to remove the nested service.
     * This site must not be <code>null</code>.
     * @return <code>true</code> if the service existed and could be removed;
     * <code>false</code> otherwise.
     */
    public boolean removeKeyBindingService(IWorkbenchSite nestedSite);
