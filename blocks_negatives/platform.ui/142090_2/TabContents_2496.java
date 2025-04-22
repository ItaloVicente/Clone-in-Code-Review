                    throws Exception {
                    section.createControls(sectionComposite, page);
                }
            };
            SafeRunnable.run(runnable);
        }
        controlsCreated = true;
    }

    /**
     * Dispose of page's sections controls.
     */
    public void dispose() {
        for (final ISection section : sections) {
            ISafeRunnable runnable = new SafeRunnable() {

                @Override
