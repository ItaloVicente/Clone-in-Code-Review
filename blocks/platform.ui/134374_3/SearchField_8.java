		triggerSequence = getTriggerSequence(QUICK_ACCESS_COMMAND_ID);
	}


	private TriggerSequence getTriggerSequence(String commandId) {
		TriggerSequence res = bindingService.getBestActiveBindingFor(commandId);
