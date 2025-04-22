		String msg = result[0];
		return new ModifyResult() {

			@Override
			public String getMessage() {
				return msg == null ? "" : msg; //$NON-NLS-1$
			}

			@Override
			public CleanupMode getCleanupMode() {
				return CleanupMode.VERBATIM;
			}
		};
