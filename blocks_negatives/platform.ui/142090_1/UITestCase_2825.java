    }

    /**
     * Open a test page with the empty perspective in a window.
     */
    public IWorkbenchPage openTestPage(IWorkbenchWindow win) {
        IWorkbenchPage[] pages = openTestPage(win, 1);
        if (pages != null) {
            return pages[0];
        }
        return null;
    }

    /**
     * Open "n" test pages with the empty perspective in a window.
     */
    public IWorkbenchPage[] openTestPage(IWorkbenchWindow win, int pageTotal) {
        try {
            IWorkbenchPage[] pages = new IWorkbenchPage[pageTotal];
            IAdaptable input = getPageInput();

            for (int i = 0; i < pageTotal; i++) {
                pages[i] = win.openPage(EmptyPerspective.PERSP_ID, input);
            }
            return pages;
        } catch (WorkbenchException e) {
        	fail("Problem opening test page", e);
            return null;
        }
    }

    /**
     * Close all pages within a window.
     */
    public void closeAllPages(IWorkbenchWindow window) {
        IWorkbenchPage[] pages = window.getPages();
        for (int i = 0; i < pages.length; i++)
            pages[i].close();
    }

    /**
     * Set whether the window listener will manage opening and closing of created windows.
     */
    protected void manageWindows(boolean manage) {
        windowListener.setEnabled(manage);
    }

    /**
     * Returns the workbench.
     *
     * @return the workbench
     * @since 3.1
     */
    protected IWorkbench getWorkbench() {
        return fWorkbench;
    }
