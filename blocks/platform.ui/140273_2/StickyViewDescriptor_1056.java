		boolean moveable = true;
		String moveableString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_MOVEABLE);
		if (moveableString != null) {
			moveable = !moveableString.equals("false"); //$NON-NLS-1$
		}
		return moveable;
	}
