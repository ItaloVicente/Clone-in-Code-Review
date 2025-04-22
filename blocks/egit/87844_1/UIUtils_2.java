				if (candidates != null)
					for (final T ref : candidates) {
						IContentProposal proposal = factory.getProposal(pattern,
								ref);
						if (proposal != null) {
							resultList.add(proposal);
						}
