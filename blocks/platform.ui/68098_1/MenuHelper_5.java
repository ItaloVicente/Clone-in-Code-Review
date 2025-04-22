	public static MCommand getMCommand(MApplication application, CommandContributionItem contribution) {
		ParameterizedCommand command = contribution.getCommand();
		if (command != null) {
			for (MCommand mcommand : application.getCommands()) {
				if (mcommand.getElementId().equals(command.getId())) {
					return mcommand;
				}
