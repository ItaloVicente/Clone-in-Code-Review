        IWorkbenchWindow window = viewPart.getSite().getWorkbenchWindow();
        if (window.equals(testWindow)) {
            return true;
        }
        return false;
    }

    /**
     * Create a new Intro area (a view, currently) in the provided window.  If there is no intro
     * descriptor for this workbench then no work is done.
     *
     * @param preferredWindow the window to create the intro in.
     */
    private void createIntro(IWorkbenchWindow preferredWindow) {
        if (this.workbench.getIntroDescriptor() == null) {
