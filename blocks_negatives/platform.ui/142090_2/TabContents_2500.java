                    throws Exception {
                    section.setInput(part, selection);
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    /**
     * Set the sections for the tab.
     *
     * @param sections the sections for the tab.
     */
    public void setSections(ISection[] sections) {
        this.sections = sections;
    }

    /**
     * Determine if the controls have been created.
     *
     * @return <code>true</code> if controls have been created.
     */
    public boolean controlsHaveBeenCreated() {
        return controlsCreated;
    }

    /**
     * If controls have been created, refresh all sections on the page.
     */
    public void refresh() {
        if (controlsCreated) {
            for (final ISection section : sections) {
                ISafeRunnable runnable = new SafeRunnable() {

                    @Override
