                    return pluginId;
                }
            });
        }
    }

    /**
     * Get a change listener for listening to resource changes.
     *
     * @return the resource change listeners
     */
    private IResourceChangeListener getChangeListener() {
        return event -> {
		    if (!WorkbenchActivityHelper.isFiltering()) {
