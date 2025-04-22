		branchGroup = new BranchSelectionComponent(checkoutGroup, repository) {
			protected LocalBranchSelectionDialog createDialog(Shell shell,
					String branch) {
				return new BranchEditDialog(shell, repository, branch);
			}
		};
		branchGroup.addModifyListener(new ModifyListener() {
