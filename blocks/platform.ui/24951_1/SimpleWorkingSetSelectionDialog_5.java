	

	@Override
	protected void upSelectedWorkingSet() {
		moveSelectedWorkingSet(-1);
	}

	@Override
	protected void downSelectedWorkingSet() {
		moveSelectedWorkingSet(+1);
	}

	private void moveSelectedWorkingSet(int diffLocation) {
		if (this.viewer.getSelection().isEmpty()) {
			return;
		}
		IStructuredSelection selection = (IStructuredSelection) this.viewer.getSelection();
		if (selection.size() > 1) {
			return;
		}
		IWorkingSet currentWorkingSet = (IWorkingSet) selection.getFirstElement();
		int idx = this.viewer.getTable().getSelectionIndex();
		int otherIdx = idx + diffLocation;
		IWorkingSet otherWorkingSet = (IWorkingSet) this.viewer.getTable().getItem(otherIdx)
				.getData();
		IWorkingSetManager manager = WorkbenchPlugin.getDefault().getWorkingSetManager();
		manager.swapIndex(currentWorkingSet, otherWorkingSet);
		availableWorkingSetsChanged();
	}
