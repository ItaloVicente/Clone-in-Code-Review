	private void registerCommandListener() {
		cmListener = new ICommandManagerListener() {
			public void commandManagerChanged(CommandManagerEvent commandManagerEvent) {
				if (commandManagerEvent.isCommandChanged()) {
					if (commandManagerEvent.isCommandDefined()) {
						final String commandId = commandManagerEvent.getCommandId();
						if (findCommand(commandId) != null) {
							return;
						}
						final Command command = commandManagerEvent.getCommandManager().getCommand(
								commandId);

						try {
							MCategory categoryModel = findCategory(command.getCategory().getId());
							final MCommand createdCommand = createCommand(command, modelService,
									categoryModel);
							application.getCommands().add(createdCommand);
						} catch (NotDefinedException e) {
							Activator.getDefault().getLogService()
									.log(0, "Failed to create command " + commandId, e); //$NON-NLS-1$
						}
					}
				}
			}
		};
		commandManager.addCommandManagerListener(cmListener);
	}

	private void unregisterCommandListener() {
		commandManager.removeCommandManagerListener(cmListener);
	}

	protected MCommand findCommand(String commandId) {
		for (MCommand cmd : application.getCommands()) {
			if (commandId.equals(cmd.getElementId())) {
				return cmd;
			}
		}
		return null;
	}

	protected MCategory findCategory(String id) {
		final List<MCategory> categories = application.getCategories();
		for (MCategory cat : categories) {
			if (id.equals(cat.getElementId())) {
				return cat;
			}
		}
		return null;
	}

