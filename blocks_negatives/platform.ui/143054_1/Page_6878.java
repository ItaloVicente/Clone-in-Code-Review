    /**
     * The <code>Page</code> implementation of this <code>IPageBookViewPage</code> method
     * stores a reference to the supplied site (the site which contains this
     * page).
     * <p>
     * Subclasses may extend.
     * </p>
     *
     * @since 2.0
     */
    @Override
    public void init(IPageSite pageSite) {
        site = pageSite;
    }
