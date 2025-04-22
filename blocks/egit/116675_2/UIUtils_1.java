				if (candidates == null) {
					return null;
				}
				Pattern pattern = patternProvider != null
						? patternProvider.apply(contents)
						: createProposalPattern(contents);
				for (final T candidate : candidates) {
					IContentProposal proposal = factory.getProposal(pattern,
							candidate);
					if (proposal != null) {
						resultList.add(proposal);
