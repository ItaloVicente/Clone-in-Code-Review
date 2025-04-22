		branchGroup = new BranchSelectionComponent(checkoutGroup, repository) {
			protected LocalBranchSelectionDialog createDialog(Shell s, String b) {
				return new LocalBranchSelectionDialog(s, repository, b) {

					@Override
					protected void createButtonsForButtonBar(Composite p) {
						super.createButtonsForButtonBar(p);

						getButton(BranchSelectionAndEditDialog.NEW).setVisible(
								false);
						getButton(Window.OK).setVisible(false);
						getButton(Window.CANCEL).setText(
								UIText.BranchSelectionAndEditDialog_OkClose);
					}
				};
			}
		};
		branchGroup.addModifyListener(new ModifyListener() {
