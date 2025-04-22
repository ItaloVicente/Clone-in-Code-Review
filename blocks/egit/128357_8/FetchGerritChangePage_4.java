	private void openProposalPopupAsync() {
		getControl().getDisplay().asyncExec(() -> {
			Control control = getControl();
			if (control != null && !control.isDisposed()) {
				contentProposer.openProposalPopup();
			}
		});
	}

