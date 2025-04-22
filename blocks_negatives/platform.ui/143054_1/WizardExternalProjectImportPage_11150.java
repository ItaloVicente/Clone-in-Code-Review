        IPath path = new Path(projectFile.getPath());

        IProjectDescription newDescription = null;

        try {
            newDescription = getWorkspace().loadProjectDescription(path);
        } catch (CoreException exception) {
        }

        if (newDescription == null) {
            this.description = null;
        } else {
            this.description = newDescription;
            this.projectNameField.setText(this.description.getName());
        }
    }

    /**
     * Return a.project file from the specified location.
     * If there isn't one return null.
     */
    private File projectFile(String locationFieldContents) {
        File directory = new File(locationFieldContents);
        if (directory.isFile()) {
