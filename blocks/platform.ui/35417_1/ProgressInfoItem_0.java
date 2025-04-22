
	private void hookCommandListener() {

		Object data = link.getData(TRIGGER_KEY);
		if (!(data instanceof ParameterizedCommand))
			return;

		Command command = ((ParameterizedCommand) data).getCommand();
		commandListener = new ICommandListener() {

			@Override
			public void commandChanged(CommandEvent commandEvent) {
				if (link == null || link.isDisposed())
					return;
				boolean enabled = commandEvent.getCommand().isEnabled();
				link.setEnabled(enabled);
			}
		};

		command.addCommandListener(commandListener);
	}

	private void removeCommandListener() {

		if (commandListener == null)
			return;

		Object data = link.getData(TRIGGER_KEY);
		if (data instanceof ParameterizedCommand)
			((ParameterizedCommand) data).getCommand().removeCommandListener(commandListener);
	}

