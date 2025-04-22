		assertSame(
				"Should have received a pre-execute event for the correct command",
				commandId, listener.preExecuteId);
		assertSame(
				"Should have received a pre-execute event with the correct event",
				event, listener.preExecuteEvent);
		assertSame(
				"Should have received a not-handled event for the correct command",
				commandId, listener.notHandledId);
		assertSame(
				"Should have received a not-handled event with the correct exception",
				exception, listener.notHandledException);
