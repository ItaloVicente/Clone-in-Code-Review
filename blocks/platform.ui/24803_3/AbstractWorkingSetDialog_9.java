		boolean missingDefaultWorkingSet = false;
		if (this.defaultWorkingSetSelector != null
				&& !this.defaultWorkingSetSelector.getControl().isDisposed()) {
			defaultWorkingSetSelector.getControl().setEnabled(
					useDefaultWorkingSetCheckbox.getSelection());
			missingDefaultWorkingSet = this.useDefaultWorkingSetCheckbox != null
				&& !this.useDefaultWorkingSetCheckbox.isDisposed()
				&& this.useDefaultWorkingSetCheckbox.getSelection()
				&& this.defaultWorkingSetSelector.getSelection().isEmpty();
			if (missingDefaultWorkingSet) {
				this.missingDefaultWorkingSetDecoration.show();
			} else {
				this.missingDefaultWorkingSetDecoration.hide();
			}
		}
		
		if (getOkButton() != null && !getOkButton().isDisposed()) {
			getOkButton().setEnabled(!missingDefaultWorkingSet);
		}
