			public ModifyResult editCommitMessage(String message,
					CleanupMode mode, char commentChar) {
				return new ModifyResult() {

					@Override
					public String getMessage() {
						return "squashed";
					}

					@Override
					public CleanupMode getCleanupMode() {
						return CleanupMode.VERBATIM;
					}
				};
