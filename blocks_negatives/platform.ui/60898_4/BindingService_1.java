		MCommand cmd = null;
		for (MCommand appCommand : application.getCommands()) {
			if (id.equals(appCommand.getElementId())) {
				cmd = appCommand;
				break;
			}
		}
