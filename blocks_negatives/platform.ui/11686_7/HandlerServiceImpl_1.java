		String commandId = command.getId();
		Object handler = lookUpHandler(context, commandId);
		if (handler == null) {
			return false;
		}

