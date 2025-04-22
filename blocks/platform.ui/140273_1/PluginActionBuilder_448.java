			contribution.contribute(menu, appendIfMissing, toolbar, appendIfMissing);
		}
	}

	protected abstract ActionDescriptor createActionDescriptor(IConfigurationElement element);

	protected BasicContribution createContribution() {
		return new BasicContribution();
	}

	protected String getTargetID(IConfigurationElement element) {
		String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_TARGET_ID);
		return value != null ? value : "???"; //$NON-NLS-1$
	}

	protected String getID(IConfigurationElement element) {
		String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		return value != null ? value : "???"; //$NON-NLS-1$
	}

	protected void readContributions(String id, String tag, String extensionPoint) {
		cache = null;
		currentContribution = null;
		targetID = id;
		targetContributionTag = tag;
		readRegistry(Platform.getExtensionRegistry(), PlatformUI.PLUGIN_ID, extensionPoint);
	}

	@Override
