	public static MMenuItem createItem(MApplication application, CommandContributionItem cci) {
		MCommand command = getMCommand(application, cci);
		if (command != null) {
			CommandContributionItemParameter data = cci.getData();
			MHandledMenuItem menuItem = MenuFactoryImpl.eINSTANCE.createHandledMenuItem();
			menuItem.setCommand(command);
			menuItem.setContributorURI(command.getContributorURI());
			if (data.label != null) {
				menuItem.setLabel(data.label);
			} else {
				menuItem.setLabel(command.getCommandName());
			}
			if (data.mnemonic != null) {
				menuItem.setMnemonics(data.mnemonic);
			}
			if (data.icon != null) {
				menuItem.setIconURI(getIconURI(data.icon, application.getContext()));
			} else {
				menuItem.setIconURI(getIconURI(command.getElementId(), application.getContext(),
						ICommandImageService.TYPE_DEFAULT));
			}
			String itemId = cci.getId();
			menuItem.setElementId(itemId == null ? command.getElementId() : itemId);
			return menuItem;
		}
		return null;
	}

