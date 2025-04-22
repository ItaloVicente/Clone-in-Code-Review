    /**
     * Checks that there is at least one project with a builder registered on
     * it.
     */
    /* package */boolean verifyBuildersAvailable(IProject[] roots) {
        try {
            for (IProject root : roots) {
                if (root.isAccessible()) {
