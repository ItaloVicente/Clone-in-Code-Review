		UpdateValueStrategy targetToModel = new UpdateValueStrategy();
		targetToModel.setBeforeSetValidator(new IValidator() {
			@Override
			public IStatus validate(Object value) {
				if (value == null || !isValidRefName(Constants.R_HEADS + value)) {
					return error(NLS.bind(InitDialog_invalidBranchName, value));
				}
				return Status.OK_STATUS;
			}
		});
		bind(context, noModelToTarget, targetToModel, DEVELOP_BRANCH_PROPERTY, developText);
		bind(context, noModelToTarget, targetToModel, MASTER_BRANCH_PROPERTY, masterText);
