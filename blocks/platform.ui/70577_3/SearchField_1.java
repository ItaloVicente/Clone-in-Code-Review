		this.bindingManagerListener = (event) -> {
			Command command = commandService.getCommand(QUICK_ACCESS_COMMAND_ID);
			ParameterizedCommand parameterizedCommand = new ParameterizedCommand(command, null);
			if (event.isActiveBindingsChanged() && event.isActiveBindingsChangedFor(parameterizedCommand)) {
				updateKeyBindingText();
			}
		};
		bindingService.addBindingManagerListener(bindingManagerListener);
