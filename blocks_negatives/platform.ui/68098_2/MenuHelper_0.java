		if (cci.getCommand() == null) {
			return null;
		}
		String id = cci.getCommand().getId();
		for (MCommand command : application.getCommands()) {
			if (id.equals(command.getElementId())) {
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
					menuItem.setIconURI(getIconURI(id, application.getContext(),
							ICommandImageService.TYPE_DEFAULT));
				}
				String itemId = cci.getId();
				menuItem.setElementId(itemId == null ? id : itemId);
				return menuItem;
