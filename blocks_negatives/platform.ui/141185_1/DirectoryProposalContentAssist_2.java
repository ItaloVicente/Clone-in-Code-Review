		getContentProposalAdapter().addContentProposalListener(e -> {
			popupActivated = false;
			updateProposals(directoryCombo.getText(), popupActivated);
		});
		getContentProposalAdapter().addContentProposalListener(new IContentProposalListener2() {

			@Override
			public void proposalPopupOpened(ContentProposalAdapter adapter) {
				popupActivated = true;
			}

			@Override
			public void proposalPopupClosed(ContentProposalAdapter adapter) {
			}
		});
