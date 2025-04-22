		boolean closeable = true;
		String closeableString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_CLOSEABLE);
		if (closeableString != null) {
			closeable = !closeableString.equals("false"); //$NON-NLS-1$
		}
		return closeable;
	}

	@Override
