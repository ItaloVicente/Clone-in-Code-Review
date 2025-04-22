                    throws Exception {
                    section.dispose();
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    /**
     * Sends the lifecycle event to the page's sections.
     */
    public void aboutToBeShown() {
        for (final ISection section : sections) {
            ISafeRunnable runnable = new SafeRunnable() {

                @Override
