
		@Override
		protected void updateButtonsEnablement(int totalCount, int checkedCount) {
			super.updateButtonsEnablement(totalCount, checkedCount);
			if (getOkButton() != null) {
				String label = NLS.bind(checkedCount == 0 ? WorkbenchMessages.SaveableHelper_Save_0_of_m
						: WorkbenchMessages.SaveableHelper_Save_n_of_m, checkedCount, totalCount);
				getOkButton().setText(label);
			}
		}
