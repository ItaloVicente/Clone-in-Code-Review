            CLIENT_VERSION = version == null ? "unknown" : version;
            CLIENT_GIT_VERSION = gitVersion == null ? "unknown" : gitVersion;

            SDK_PACKAGE_NAME_AND_VERSION = String.format("couchbase-java-client/%s (git: %s, core: %s)",
                    CLIENT_VERSION,
                    CLIENT_GIT_VERSION,
                    CORE_GIT_VERSION);

            SDK_USER_AGENT = String.format("%s (%s/%s %s; %s %s)",
