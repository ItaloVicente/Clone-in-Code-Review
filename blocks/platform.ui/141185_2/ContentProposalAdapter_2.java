				control.getDisplay().asyncExec(() -> {
					IContentProposal[] filteredProposals = filterProposals(newProposals, filterText);
					if (isProposalPopupOpen() && proposals.length > 0 && Arrays.equals(proposals, filteredProposals)) {
						return;
					}
					setProposals(filteredProposals);
				});
