
			final Command command = commandService.getCommand(currentCommandId);
			ParameterizedCommand pcmd = new ParameterizedCommand(command, null);
			if (command != null && ehandlerService.canExecute(pcmd)) {
				try {
					Collection<?> combinations = ParameterizedCommand.generateCombinations(command);
					for (Iterator<?> it = combinations.iterator(); it.hasNext();) {
						ParameterizedCommand pc = (ParameterizedCommand) it.next();
						String id = pc.serialize();
						idToCommand.put(id, new CommandElement(pc, id, this));
