
    protected static int[] extractVersion(String stringVersion) {
        String[] splitVersion = stringVersion.split("[^\\d]+");
        int[] version = new int[2];

        version[0] = Integer.parseInt(splitVersion[0]); //major
        version[1] = splitVersion.length < 2 ? 0 : Integer.parseInt(splitVersion[1]); //minor
        return version;
    }
