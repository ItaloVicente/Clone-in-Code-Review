
	@Override
	protected void updateButtonAvailability() {
		super.updateButtonAvailability();
		if (this.upButton != null && !this.upButton.isDisposed()) {
			if (!this.listViewer.getSelection().isEmpty()) {
				IStructuredSelection selection = (IStructuredSelection) this.listViewer
						.getSelection();
				if (!selection.isEmpty()) {
					int[] selectionIndices = this.listViewer.getTable().getSelectionIndices();
					Arrays.sort(selectionIndices);
					{
						boolean enabled = false;
						int index = 0;
						while (!enabled && index < selectionIndices.length) {
							if (selectionIndices[index] != index) {
								enabled = true;
							}
							index++;
						}
						this.upButton.setEnabled(enabled);
					}
					{
						boolean enabled = false;
						int index = 0;
						while (!enabled && index < selectionIndices.length) {
							if (selectionIndices[index] != this.listViewer.getTable().getItemCount()
									- selectionIndices.length + index) {
								enabled = true;
							}
							index++;
						}
						this.downButton.setEnabled(enabled);
					}
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
		int[] selectedIndices = listViewer.getTable().getSelectionIndices();
		Arrays.sort(selectedIndices);
		if (diffLocation < 0) {
			for (int i = 0; i < selectedIndices.length / 2; i++) {
				int tmp = selectedIndices[i];
				selectedIndices[i] = selectedIndices[selectedIndices.length - 1 - i];
				selectedIndices[selectedIndices.length - 1 - i] = tmp;
			}
		}
		IWorkingSetManager manager = WorkbenchPlugin.getDefault().getWorkingSetManager();
		for (int selectedIndex : selectedIndices) {
			TableItem item = listViewer.getTable().getItem(selectedIndex);
			IWorkingSet currentWorkingSet = (IWorkingSet) item.getData();
			int otherIdx = selectedIndex + diffLocation;
			if (otherIdx >= 0 && otherIdx <= listViewer.getTable().getItemCount()) {
				IWorkingSet otherWorkingSet = (IWorkingSet) this.listViewer.getTable().getItem(otherIdx).getData();
				manager.swapIndex(currentWorkingSet, otherWorkingSet);
			}
		}
		availableWorkingSetsChanged();
	}
