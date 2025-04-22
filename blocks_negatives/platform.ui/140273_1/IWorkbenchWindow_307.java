    /**
     * Creates and opens a new workbench page. The perspective of the new page
     * is defined by the specified perspective ID. The new page become active.
     * <p>
     * <b>Note:</b> Since release 2.0, a window is limited to contain at most
     * one page. If a page exist in the window when this method is used, then
     * another window is created for the new page. Callers are strongly
     * recommended to use the <code>IWorkbench.showPerspective</code> APIs to
     * programmatically show a perspective.
     * </p>
     *
     * @param perspectiveId
     *            the perspective id for the window's initial page
     * @param input
     *            the page input, or <code>null</code> if there is no current
     *            input. This is used to seed the input for the new page's
     *            views.
     * @return the new workbench page
     * @exception WorkbenchException
     *                if a page could not be opened
     *
     * @see IWorkbench#showPerspective(String, IWorkbenchWindow, IAdaptable)
     */
    IWorkbenchPage openPage(String perspectiveId, IAdaptable input)
            throws WorkbenchException;
