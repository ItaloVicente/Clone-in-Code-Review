	}

	interface IStatusManagerWrapper {
		default void handle(IStatus status, int style) {
			StatusManager.getManager().handle(status, style);
		}
	}
