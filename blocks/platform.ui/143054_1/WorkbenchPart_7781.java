
		return Platform.getAdapterManager().getAdapter(this, adapter);
	}

	protected IConfigurationElement getConfigurationElement() {
		return configElement;
	}

	protected Image getDefaultImage() {
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEF_VIEW);
	}

	@Override
