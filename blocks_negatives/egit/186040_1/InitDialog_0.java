		UpdateValueStrategy prefixTargetToModel = new UpdateValueStrategy();
		prefixTargetToModel.setBeforeSetValidator(new IValidator() {
			@Override
			public IStatus validate(Object value) {
				if (value == null
						|| !isValidRefName(R_HEADS + value + DUMMY_POSTFIX)) {
					return error(NLS.bind(InitDialog_invalidPrefix, value));
				}
				return Status.OK_STATUS;
