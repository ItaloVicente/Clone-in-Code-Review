		IContentProposalProvider proposalProvider = new ReviewerProposalProvider(
				new IPersonProvider() {
					public Collection<Person> getPeople() {
						return PreferenceStorePersonProvider.getInstance()
								.getPeople();
					}
				});

