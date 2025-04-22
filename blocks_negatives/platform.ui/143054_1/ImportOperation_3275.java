    }

    /**
     * Set Whether groups and links will be created instead of files and folders
     *
     * @param virtualFolders
     * @since 3.6
     */
    public void setVirtualFolders(boolean virtualFolders) {
        createVirtualFolder = virtualFolders;
    }

    /**
     * Set Whether links will be created instead of files and folders
     *
     * @param links
     * @since 3.6
     */
    public void setCreateLinks(boolean links) {
        createLinks = links;
    }

    /**
     * Set a variable relative to which the links are created
     *
     * @param variable
     * @since 3.6
     */
    public void setRelativeVariable(String variable) {
        relativeVariable = variable;
    }
