			if (asyncProposalComputation) {
				CompletableFuture.runAsync(() -> {
					_recomputeProposals(filterText);
				});
			} else {
				_recomputeProposals(filterText);
			}
		}

		private void _recomputeProposals(String filterText) {
