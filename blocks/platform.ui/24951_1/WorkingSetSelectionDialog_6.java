
	@Override
	protected void updateButtonAvailability() {
		super.updateButtonAvailability();
		if (this.upButton != null && !this.upButton.isDisposed()) {
			if (!this.listViewer.getSelection().isEmpty()) {
				IStructuredSelection selection = (IStructuredSelection) this.listViewer
						.getSelection();
				if (selection.size() == 1) {
					int selectionIndex = this.listViewer.getTable().getSelectionIndex();
					this.upButton.setEnabled(selectionIndex > 0);
					this.downButton.setEnabled(selectionIndex < this.listViewer.getTable()
							.getItemCount() - 1);
				}
			}
		}
	}

	@Override
	protected void upSelectedWorkingSet() {
		moveSelectedWorkingSet(-1);
	}

	@Override
	protected void downSelectedWorkingSet() {
		moveSelectedWorkingSet(+1);
	}

	private void moveSelectedWorkingSet(int diffLocation) {
		if (this.listViewer.getSelection().isEmpty()) {
			return;
		}
		IStructuredSelection selection = (IStructuredSelection) listViewer.getSelection();
		if (selection.size() > 1) {
			return;
		}
		IWorkingSet currentWorkingSet = (IWorkingSet) selection.getFirstElement();
		int idx = this.listViewer.getTable().getSelectionIndex();
		int otherIdx = idx + diffLocation;
		IWorkingSet otherWorkingSet = (IWorkingSet) this.listViewer.getTable().getItem(otherIdx)
				.getData();
		IWorkingSetManager manager = WorkbenchPlugin.getDefault().getWorkingSetManager();
		manager.swapIndex(currentWorkingSet, otherWorkingSet);
		availableWorkingSetsChanged();
	}
