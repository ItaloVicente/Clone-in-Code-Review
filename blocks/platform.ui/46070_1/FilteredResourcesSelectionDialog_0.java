	private class GroupResourcesByLocationAction extends Action {

		public GroupResourcesByLocationAction() {
			super(IDEWorkbenchMessages.FilteredResourcesSelectionDialog_groupResourcesWithSameUndelyingLocation,
					IAction.AS_CHECK_BOX);
		}

		@Override
		public void run() {
			FilteredResourcesSelectionDialog.this.filterResourceByLocation.setEnabled(isChecked());
			scheduleRefresh();
			applyFilter();
		}
	}

