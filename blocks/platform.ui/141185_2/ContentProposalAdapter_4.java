		if (asyncProposalComputation) {
			CompletableFuture.runAsync(() -> {
				_openProposalPopup(autoActivated);
			});
		} else {
			_openProposalPopup(autoActivated);
		}
	}

	private void _openProposalPopup(boolean autoActivated) {
