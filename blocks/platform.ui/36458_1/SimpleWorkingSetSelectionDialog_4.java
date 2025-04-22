	@Override
	protected void updateButtonAvailability() {
		super.updateButtonAvailability();
		if (this.upButton != null && !this.upButton.isDisposed()) {
			if (!this.viewer.getSelection().isEmpty()) {
				IStructuredSelection selection = (IStructuredSelection) this.viewer.getSelection();
				if (selection.size() == 1) {
					int selectionIndex = this.viewer.getTable().getSelectionIndex();
					this.upButton.setEnabled(selectionIndex > 0);
					this.downButton.setEnabled(selectionIndex < this.viewer.getTable()
							.getItemCount() - 1);
				}
			}
		}
	}

