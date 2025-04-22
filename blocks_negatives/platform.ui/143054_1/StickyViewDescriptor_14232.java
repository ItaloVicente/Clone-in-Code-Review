    	boolean closeable = true;
    	String closeableString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_CLOSEABLE);
        if (closeableString != null) {
        }
        return closeable;
    }

    @Override
