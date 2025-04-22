        return initialDefault;
    }

    /**
     * Set this data's initialDefault parameter to a properly formatted version
     * of the argument directory string. The proper format is to the platform
     * appropriate separator character without meaningless leading or trailing
     * separator characters.
     */
    private void setInitialDefault(String dir) {
        if (dir == null || dir.length() <= 0) {
            initialDefault = null;
            return;
        }

        dir = new Path(dir).toOSString();
        while (dir.charAt(dir.length() - 1) == File.separatorChar) {
