    }


    /**
     * Return the manager that maps project nature ids to images.
     */
    public ProjectImageRegistry getProjectImageRegistry() {
        if (projectImageRegistry == null) {
            projectImageRegistry = new ProjectImageRegistry();
            projectImageRegistry.load();
        }
        return projectImageRegistry;
    }

    /**
     * Returns the marker image provider registry for the workbench.
     *
     * @return the marker image provider registry
     */
    public MarkerImageProviderRegistry getMarkerImageProviderRegistry() {
        if (markerImageProviderRegistry == null) {
            markerImageProviderRegistry = new MarkerImageProviderRegistry();
        }
        return markerImageProviderRegistry;
    }
