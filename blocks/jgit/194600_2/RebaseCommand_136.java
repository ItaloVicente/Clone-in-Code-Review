	public interface InteractiveHandler2 extends InteractiveHandler {

		@NonNull
		ModifyResult editCommitMessage(@NonNull String message
				@NonNull CleanupMode mode

		@Override
		default String modifyCommitMessage(String message) {
			ModifyResult result = editCommitMessage(
					message == null ? "" : message
					'#');
			return result.getMessage();
		}

		interface ModifyResult {

			@NonNull
			String getMessage();

			@NonNull
			CleanupMode getCleanupMode();

			boolean shouldAddChangeId();
		}
	}
