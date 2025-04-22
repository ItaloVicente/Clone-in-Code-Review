        return AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId,
                icon);
    }

    /**
     * Returns the working set page id.
     *
     * @return the working set page id.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the working set page class name
     *
     * @return the working set page class name or <code>null</code> if
     *  no page class name has been provided by the extension
     */
    public String getPageClassName() {
        return pageClassName;
    }

    /**
     * Returns the name of the working set element type the
     * page works with.
     *
     * @return the working set element type name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the working set updater class name
     *
     * @return the working set updater class name or <code>null</code> if
     *  no updater class name has been provided by the extension
     */
    public String getUpdaterClassName() {
    	return updaterClassName;
    }

    /**
