
			final IHandlerService service = locator.getService(IHandlerService.class);
			try {
				service.executeCommand(command, event);
			} catch (final CommandException e) {
				/*
				 * TODO There should be an API on IHandlerService that handles the exceptions.
				 */
			}
