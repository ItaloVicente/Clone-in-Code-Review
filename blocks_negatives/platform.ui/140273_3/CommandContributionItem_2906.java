	public CommandContributionItem(IServiceLocator serviceLocator, String id,
			String commandId, Map parameters, ImageDescriptor icon,
			ImageDescriptor disabledIcon, ImageDescriptor hoverIcon,
			String label, String mnemonic, String tooltip, int style) {
		this(new CommandContributionItemParameter(serviceLocator, id,
				commandId, parameters, icon, disabledIcon, hoverIcon, label,
				mnemonic, tooltip, style, null, false));
