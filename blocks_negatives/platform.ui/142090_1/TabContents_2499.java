                    throws Exception {
                    section.aboutToBeHidden();
                }
            };
            SafeRunnable.run(runnable);
        }
    }

    /**
     * Sets page's sections input objects.
     *
     * @param part
     * @param selection
     */
    public void setInput(final IWorkbenchPart part, final ISelection selection) {
        for (final ISection section : sections) {
            ISafeRunnable runnable = new SafeRunnable() {

                @Override
