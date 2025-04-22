            String versionString = props.getProperty(WORKSPACE_VERSION_REFERENCE_BUNDLE);
            if (versionString != null) {
                return Version.parseVersion(versionString);
            }

            return null;
