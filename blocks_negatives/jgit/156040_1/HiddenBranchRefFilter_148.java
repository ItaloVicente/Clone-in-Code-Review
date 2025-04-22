    /**
     * Checks if a branch name matches the hidden branch regexp
     * @param branch the branch you want to check.
     * @return return if the branch is hidden or not
     */
    public static boolean isHidden(String branch) {
        return pattern.matcher(branch).matches();
    }
