    /**
     * Disposes of this intro part.
     * <p>
     * This is the last method called on the <code>IIntroPart</code>.  At this
     * point the part controls (if they were ever created) have been disposed as part
     * of an SWT composite.  There is no guarantee that createPartControl() has been
     * called, so the part controls may never have been created.
     * </p>
     * <p>
     * Within this method a part may release any resources, fonts, images, etc.&nbsp;
     * held by this part.  It is also very important to deregister all listeners
     * from the workbench.
     * </p>
     * <p>
     * Clients should not call this method (the workbench calls this method at
     * appropriate times).
     * </p>
     */
    void dispose();
