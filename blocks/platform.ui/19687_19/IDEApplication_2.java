            String versionString = props.getProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME);
            if (versionString != null) {
                return Version.parseVersion(versionString);
            }

            return null;
