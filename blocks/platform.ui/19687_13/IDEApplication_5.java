
            if (WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION != null) {
                props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME, WORKSPACE_CHECK_REFERENCE_BUNDLE_VERSION.toString());
            }

            props.setProperty(WORKSPACE_CHECK_REFERENCE_BUNDLE_NAME_LEGACY, WORKSPACE_CHECK_LEGACY_VERSION_INCREMENTED);

            props.store(output, null);
