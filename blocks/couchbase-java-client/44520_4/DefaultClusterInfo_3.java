    @Override
    public boolean checkAvailable(CouchbaseFeature feature) {
        Version minVersion = getMinVersion();
        return feature.isAvailableOn(minVersion);
    }

    @Override
    public Version getMinVersion() {
        List<Version> versions = getAllVersions();
        if (versions.isEmpty()) {
            return Version.NO_VERSION;
        } else if (versions.size() == 1) {
            return versions.get(0);
        } else {
            Version minVersion = Version.VERY_BIG;
            for (Version version : versions) {
                if (version.compareTo(minVersion) < 0) {
                    minVersion = version;
                }
            }
            return minVersion;
        }
    }

    @Override
    public List<Version> getAllVersions() {
        try {
            JsonObject raw = raw();
            if (!raw.containsKey("nodes")) {
                return Collections.emptyList();
            }
            List<Version> result = new ArrayList<Version>();
            JsonArray nodes = raw.getArray("nodes");
            for (int i = 0; i < nodes.size(); i++) {
                JsonObject node = nodes.getObject(i);
                if (node.containsKey("version")) {
                    String versionFull = node.getString("version");
                    Version version = Version.parseVersion(versionFull);
                    result.add(version);
                }
            }
            return result;
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

