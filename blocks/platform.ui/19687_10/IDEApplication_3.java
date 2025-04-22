            Properties props = new Properties();

            props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME, WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION.toString());
            props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY, String.valueOf(WORKSPACE_CHECK_LEGACY_VERSION_UPDATED));

            props.store(output, null);
