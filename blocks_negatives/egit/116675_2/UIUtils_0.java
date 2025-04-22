				if (candidates != null) {
					for (final T candidate : candidates) {
						IContentProposal proposal = factory.getProposal(pattern,
								candidate);
						if (proposal != null) {
							resultList.add(proposal);
						}
