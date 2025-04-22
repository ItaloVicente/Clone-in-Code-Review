    /**
     * Implement this method to add an instance of PropertyPage class to the
     * property page manager.
     * @param manager the contributor manager onto which to contribute the
     * property page.
     * @param object the type for which page should be contributed.
     * @return true the page that was added, <code>null</code> if no page was added.
     */
    public PreferenceNode contributePropertyPage(PropertyPageManager manager,
            Object object);
