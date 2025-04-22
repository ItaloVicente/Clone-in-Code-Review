        }
    }

    /**
     * Return a sorted array of the names of the projects that are already in the currently
     * displayed names.
     * @return String[]
     * @param allProjects - all of the projects in the workspace
     * @param currentlyDisplayed - the names of the projects already being displayed
     */
    private String[] sortedDifference(IProject[] allProjects,
            String[] currentlyDisplayed) {
