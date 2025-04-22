    /**
     * @return the major and minor parts of the given version
     */
    private static Version toMajorMinorVersion(Version version) {
        return new Version(version.getMajor(), version.getMinor(), 0);
    }
