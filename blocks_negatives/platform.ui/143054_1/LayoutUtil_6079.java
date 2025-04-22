    /**
     * Should be called whenever a control's contents have changed. Will
     * trigger a layout parent controls if necessary.
     *
     * @param changedControl
     */
    public static void resize(Control changedControl) {
        Composite parent = changedControl.getParent();

        Layout parentLayout = parent.getLayout();

        if (parentLayout instanceof ICachingLayout) {
            ((ICachingLayout) parentLayout).flush(changedControl);
        }

        if (parent instanceof Shell) {
            parent.layout(true);
        } else {
            Rectangle currentBounds = parent.getBounds();

            resize(parent);

            if (currentBounds.equals(parent.getBounds())) {
                parent.layout(true);
            }
        }
    }
