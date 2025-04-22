			deselectAllButton.setEnabled(!allWorkingSets.isEmpty());
		}

		if (this.useDefaultWorkingSetCheckbox != null
				&& !this.useDefaultWorkingSetCheckbox.isDisposed()) {
			if (allWorkingSets.isEmpty()) {
				this.useDefaultWorkingSetCheckbox.setSelection(false);
			}
			this.useDefaultWorkingSetCheckbox.setEnabled(!allWorkingSets.isEmpty());
		}
		if (this.defaultWorkingSetSelector != null
				&& !this.defaultWorkingSetSelector.getControl().isDisposed()) {
			this.defaultWorkingSetSelector.setInput(allWorkingSets);
			if (allWorkingSets.contains(this.defaultWorkingSet)) {
				this.defaultWorkingSetSelector.setSelection(new StructuredSelection(
						this.defaultWorkingSet));
			}
			this.defaultWorkingSetSelector.getControl().setEnabled(!allWorkingSets.isEmpty());
