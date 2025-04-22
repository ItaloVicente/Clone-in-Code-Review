		return event -> {
			String commandId;
			switch (event.button) {
			case 4:
			case 8:
				commandId = IWorkbenchCommandConstants.NAVIGATE_BACKWARD_HISTORY;
				break;
			case 5:
			case 9:
				commandId = IWorkbenchCommandConstants.NAVIGATE_FORWARD_HISTORY;
				break;
			default:
				return;
			}
