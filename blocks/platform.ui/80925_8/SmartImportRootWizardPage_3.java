		setErrorMessage(null);
		if (tree.getCheckedElements().length == 0) {
			this.proposalSelectionDecorator.show();
			setErrorMessage(this.proposalSelectionDecorator.getDescriptionText());
		} else {
			this.proposalSelectionDecorator.hide();
		}

		if (!sourceIsValid()) {
