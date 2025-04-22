    }

    /**
     * FOR INTERNAL WORKBENCH USE ONLY.
     *
     * Returns the path to a location in the file system that can be used
     * to persist/restore state between workbench invocations.
     * If the location did not exist prior to this call it will  be created.
     * Returns <code>null</code> if no such location is available.
     *
     * @return path to a location in the file system where this plug-in can
     * persist data between sessions, or <code>null</code> if no such
     * location is available.
     * @since 3.1
     */
    private IPath getStateLocationOrNull() {
        try {
            return getStateLocation();
        } catch (IllegalStateException e) {
            return null;
        }
    }
