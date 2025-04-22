				ActionFactory.PASTE, () -> {
					if (doPaste(refText) && contentProposer != null) {
						refText.getDisplay().asyncExec(() -> {
							if (!refText.isDisposed()) {
								contentProposer.openProposalPopup();
							}
						});
					}
				}));
