				throw new BinaryBlobException() {

					private static final long serialVersionUID = 1L;

					@Override
					public Throwable fillInStackTrace() {
						return this;
					}
				};
