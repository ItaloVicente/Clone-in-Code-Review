            }
        });
    }

    /**
     * Return a string containing the path that is closest to the current
     * selection in the text widget. This starts with the current value and
     * works toward the root until there is a directory for which File.exists
     * returns true. Return the current working dir if the text box does not
     * contain a valid path.
     *
     * @return closest parent that exists or an empty string
     */
    private String getInitialBrowsePath() {
        File dir = new File(getWorkspaceLocation());
        while (dir != null && !dir.exists()) {
