     * Disposes of this page.
     * <p>
     * This is the last method called on the <code>IPage</code>. Implementors should
     * clean up any resources associated with the page.
     * </p>
     * Callers of this method should ensure that the page's control (if it exists)
     * has been disposed before calling this method. However, for backward compatibilty,
     * implementors must also ensure that the page's control has been disposed before
     * this method returns.
     * </p>
     * <p>
     * Note that there is no guarantee that createControl() has been called,
     * so the control may never have been created.
     * </p>
     */
