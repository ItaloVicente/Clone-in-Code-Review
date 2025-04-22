		MCommand command = getMCommand(application, cci);
		if (command != null) {
			CommandContributionItemParameter data = cci.getData();
			MHandledToolItem toolItem = MenuFactoryImpl.eINSTANCE.createHandledToolItem();
			toolItem.setCommand(command);
			toolItem.setContributorURI(command.getContributorURI());
			toolItem.setVisible(cci.isVisible());

			String iconURI = null;
			String disabledIconURI = null;

			toolItem.setType(ItemType.PUSH);
			if (data.style == CommandContributionItem.STYLE_CHECK)
				toolItem.setType(ItemType.CHECK);
			else if (data.style == CommandContributionItem.STYLE_RADIO)
				toolItem.setType(ItemType.RADIO);
