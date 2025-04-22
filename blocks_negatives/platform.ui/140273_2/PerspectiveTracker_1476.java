    /**
     * Determines the active perspective in the window
     * and calls <code>update(IPerspectiveDescriptor)</code>.
     */
    private void update() {
        if (window != null) {
            IPerspectiveDescriptor persp = null;
            IWorkbenchPage page = window.getActivePage();
            if (page != null) {
                persp = page.getPerspective();
            }
            update(persp);
        }
    }
