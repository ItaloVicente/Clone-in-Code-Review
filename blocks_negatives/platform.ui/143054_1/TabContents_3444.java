                    throws Exception {
                    section.aboutToBeShown();
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    /**
     * Sends the lifecycle event to the page's sections.
     */
    public void aboutToBeHidden() {
        for (final ISection section : sections) {
            ISafeRunnable runnable = new SafeRunnable() {

                @Override
