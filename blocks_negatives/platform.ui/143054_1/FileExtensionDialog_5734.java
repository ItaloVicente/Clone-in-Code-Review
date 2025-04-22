        return filename.substring(index + 1, filename.length());
    }

    /**
     * Get the name.
     *
     * @return the name
     */
    public String getName() {

        int index = filename.lastIndexOf('.');
        if (index == -1) {
