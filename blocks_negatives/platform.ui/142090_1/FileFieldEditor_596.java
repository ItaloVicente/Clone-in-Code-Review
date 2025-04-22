        }

        return null;
    }

    /**
     * Sets this file field editor's file extension filter.
     *
     * @param extensions a list of file extension, or <code>null</code>
     * to set the filter to the system's default value
     */
    public void setFileExtensions(String[] extensions) {
        this.extensions = extensions;
    }

    /**
     * Sets the initial path for the Browse dialog.
     * @param path initial path for the Browse dialog
     * @since 3.6
     */
    public void setFilterPath(File path) {
    	filterPath = path;
    }
