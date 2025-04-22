		UpdateValueStrategy developUpdateStrategy = new UpdateValueStrategy();
		developUpdateStrategy.setBeforeSetValidator(new BranchValidator());
		bind(context, noModelToTarget, developUpdateStrategy, DEVELOP_BRANCH_PROPERTY, developText);

		UpdateValueStrategy masterUpdateStrategy = new UpdateValueStrategy();
		masterUpdateStrategy.setBeforeSetValidator(new BranchValidator());
		masterUpdateStrategy.setAfterConvertValidator(new BranchExistsValidator(branchList));
		bind(context, noModelToTarget, masterUpdateStrategy, MASTER_BRANCH_PROPERTY, masterText);
