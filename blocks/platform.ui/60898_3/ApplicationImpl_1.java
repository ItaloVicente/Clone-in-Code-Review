	public MCommand getCommand(String elementId) {
		if (elementIdToCommandMap == null) {
			Map<String, MCommand> result = new HashMap<String, MCommand>();
			for (MCommand command : getCommands()) {
				MCommand otherCommand = result.put(command.getElementId(), command);
				if (otherCommand != null) {
					result.put(command.getElementId(), otherCommand);
				}
			}

			elementIdToCommandMap = result;
		}
		return elementIdToCommandMap.get(elementId);
	}

