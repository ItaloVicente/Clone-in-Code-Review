    /**
     * Creates a new resource link wizard page.
     *
     * @param pageName the name of the page
     * @param type specifies the type of resource to link to.
     * 	<code>IResource.FILE</code> or <code>IResource.FOLDER</code>
     */
    public WizardNewLinkPage(String pageName, int type) {
        super(pageName);
        this.type = type;
        setPageComplete(true);
    }
