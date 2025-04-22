        return minNodeVersion()[0] >= 4;
    }

    public static void assumeMinimumVersionCompatible(int major, int minor) throws Exception {
        int[] version = minNodeVersion();
        Assume.assumeTrue("Detected Couchbase " + version[0] + "." + version[1] + ", needed " + major + "." + minor,
               version[0] > major || (version[0] == major && version[1] >= minor));
    }

    public static int[] minNodeVersion() throws Exception {
