        titleImage = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
    }

    /**
     * Sets the part site.
     * <p>
     * Subclasses must invoke this method from <code>IEditorPart.init</code>
     * and <code>IViewPart.init</code>.
     *
     * @param site the workbench part site
     */
    protected void setSite(IWorkbenchPartSite site) {
        checkSite(site);
        this.partSite = site;
    }

    /**
     * Checks that the given site is valid for this type of part.
     * The default implementation does nothing.
     *
     * @param site the site to check
     * @since 3.1
     */
    protected void checkSite(IWorkbenchPartSite site) {
    }

    /**
     * Sets or clears the title of this part. Clients should call this method instead
     * of overriding getTitle.
     * <p>
     * This may change a title that was previously set using setPartName or setContentDescription.
     * </p>
     *
     * @deprecated new code should use setPartName and setContentDescription
     *
     * @param title the title, or <code>null</code> to clear
     */
    @Deprecated
