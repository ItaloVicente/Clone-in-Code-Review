        if (bounds != null) {
            getShell().setBounds(bounds);
        } else {
            super.initializeBounds();
        }
    }

    /**
     * Creates the contents of this dialog, initializes the
     * listener and the update thread.
     */
    @Override
