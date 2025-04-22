			return new IValidator() {

				@Override
				public IStatus validate(Object value) {
					return Status.OK_STATUS;
				}
			};
