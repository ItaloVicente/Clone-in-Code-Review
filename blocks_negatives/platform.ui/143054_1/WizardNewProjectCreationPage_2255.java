        return getProjectNameFieldValue();
    }

    /**
     * Returns the value of the project name field
     * with leading and trailing spaces removed.
     *
     * @return the project name in the field
     */
    private String getProjectNameFieldValue() {
        if (projectNameField == null) {
