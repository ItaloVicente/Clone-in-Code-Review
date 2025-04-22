			public ModifyResult editCommitMessage(String message,
					CleanupMode mode, char commentChar) {
				String edited = promptCommitMessage(message, mode, commentChar);
				return new ModifyResult() {

					@Override
					public String getMessage() {
						return edited == null ? "" : edited; //$NON-NLS-1$
					}

					@Override
					public CleanupMode getCleanupMode() {
						return CleanupMode.VERBATIM;
					}
				};
