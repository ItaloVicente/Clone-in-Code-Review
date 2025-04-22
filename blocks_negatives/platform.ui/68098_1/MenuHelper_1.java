		String id = cci.getCommand().getId();
		for (MCommand command : application.getCommands()) {
			if (id.equals(command.getElementId())) {
				CommandContributionItemParameter data = cci.getData();
				MHandledToolItem toolItem = MenuFactoryImpl.eINSTANCE.createHandledToolItem();
				toolItem.setCommand(command);
				toolItem.setContributorURI(command.getContributorURI());
				toolItem.setVisible(cci.isVisible());

				String iconURI = null;
				String disabledIconURI = null;
