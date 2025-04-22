		case IDialogConstants.OK_ID:
			super.buttonPressed(buttonId);
		}
		if (shouldCheckout) {
			super.buttonPressed(buttonId);
			BranchOperationUI op = BranchOperationUI.checkout(repository, target);
			op.start();
