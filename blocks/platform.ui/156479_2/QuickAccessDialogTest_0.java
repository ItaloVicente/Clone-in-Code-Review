		processEventsUntil(
				() -> activeWorkbenchWindow.getActivePage() != null
						&& activeWorkbenchWindow.getActivePage().getActivePart() != null
						&& quickAccessElementText
								.equalsIgnoreCase(activeWorkbenchWindow.getActivePage().getActivePart().getTitle()),
				TIMEOUT);
