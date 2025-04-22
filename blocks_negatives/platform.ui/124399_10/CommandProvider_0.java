			final Collection commandIds = commandService.getDefinedCommandIds();
			final Iterator commandIdItr = commandIds.iterator();
			while (commandIdItr.hasNext()) {
				final String currentCommandId = (String) commandIdItr.next();
				final Command command = commandService
						.getCommand(currentCommandId);
				ParameterizedCommand pcmd = new ParameterizedCommand(command, null);
				if (command != null && ehandlerService.canExecute(pcmd)) {
					try {
						Collection combinations = ParameterizedCommand
								.generateCombinations(command);
						for (Iterator it = combinations.iterator(); it
								.hasNext();) {
							ParameterizedCommand pc = (ParameterizedCommand) it.next();
							String id = pc.serialize();
							idToElement.put(id,
									new CommandElement(pc, id, this));
						}
					} catch (final NotDefinedException e) {
