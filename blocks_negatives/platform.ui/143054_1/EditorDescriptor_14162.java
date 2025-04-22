		else if (getLauncher() != null) {
        	return EditorDescriptor.OPEN_EXTERNAL;
        } else if (getFileName() != null) {
            return EditorDescriptor.OPEN_EXTERNAL;
        } else if (getPluginId() != null) {
        	return EditorDescriptor.OPEN_INTERNAL;
        }
        else {
        }
	}

	/**
     * Set the class name of an internal editor.
     */
    /* package */void setClassName(String newClassName) {
        className = newClassName;
    }

    /**
     * Set the configuration element which contributed this editor.
     */
    /* package */void setConfigurationElement(
            IConfigurationElement newConfigurationElement) {
        configurationElement = newConfigurationElement;
    }

    /**
     * Set the filename of an external editor.
     */
    /* package */void setFileName(String aFileName) {
        fileName = aFileName;
    }

    /**
     * Set the id of the editor.
     * For internal editors this is the id as provided in the extension point
     * For external editors it is path and filename of the editor
     */
    /* package */void setID(String anID) {
        Assert.isNotNull(anID);
        id = anID;
    }

    /**
     * The name of the image to use for this editor.
     */
    /* package */void setImageFilename(String aFileName) {
        imageFilename = aFileName;
    }

    /**
     * Sets the new launcher class name
     *
     * @param newLauncher the new launcher
     */
    /* package */void setLauncher(String newLauncher) {
        launcherName = newLauncher;
    }

    /**
     * The label to show for this editor.
     */
    /* package */void setName(String newName) {
        editorName = newName;
    }

    /**
     * Sets the open mode of this editor descriptor.
     *
     * @param mode the open mode
     *
     * @issue this method is public as a temporary fix for bug 47600
     */
    public void setOpenMode(int mode) {
        openMode = mode;
    }

    /**
     * The id of the plugin which contributed this editor, null for external editors.
     */
    /* package */void setPluginIdentifier(String anID) {
        pluginIdentifier = anID;
    }

    /**
     * Set the receivers program.
     * @param newProgram
     */
    /* package */void setProgram(Program newProgram) {

        this.program = newProgram;
        if (editorName == null) {
