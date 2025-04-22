        declareImages();
    }

    /**
     * Disposes and clears the workbench images.
     * Called when the workbench is shutting down.
     *
     * @since 3.1
     */
    public static void dispose() {
        if (imageRegistry != null) {
            imageRegistry.dispose();
            imageRegistry = null;
            descriptors = null;
        }
    }
