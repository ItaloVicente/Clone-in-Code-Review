            Properties props = new Properties();

            props.setProperty(WORKSPACE_VERSION_REFERENCE_BUNDLE, version);
            props.setProperty(WORKSPACE_VERSION_REFERENCE_BUNDLE_LEGACY, String.valueOf(WORKSPACE_VERSION_LEGACY + 1));

            props.store(output, null);
