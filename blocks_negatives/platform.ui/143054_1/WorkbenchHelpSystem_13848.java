            for (Object object : objects) {
                if (object == pluggableHelpUI) {
                    isInitialized = false;
                    pluggableHelpUI = null;
                    helpCompatibilityWrapper = null;
                    PlatformUI.getWorkbench().getExtensionTracker()
							.unregisterHandler(handler);
                }
            }
        }
    };
