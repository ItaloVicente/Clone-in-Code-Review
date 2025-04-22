
	private static final class ReviewerProposalProvider implements
			IContentProposalProvider {

		private Collection<Person> people;

		public ReviewerProposalProvider(IPersonProvider personProvider) {
			people = personProvider.getPeople();
		}

		public IContentProposal[] getProposals(String contents, int position) {
			String prefix = null;

			int prevSeparator = position - 1;
			while (prevSeparator >= 0
					&& contents.charAt(prevSeparator) != REVIEWERS_SEPARATOR) {
				prevSeparator--;
			}

			prefix = contents.substring(prevSeparator + 1, position);
			prefix = prefix.trim();

			String proposalContent = null;
			List<IContentProposal> result = new LinkedList<IContentProposal>();
			for (Person person : people) {
				if (person.getLogin().startsWith(prefix)
						|| (person.getName() != null && person.getName()
								.startsWith(prefix))) {

					proposalContent = person.getName() != null ? //
					String.format("%s %s%s%s", person.getName(), //$NON-NLS-1$
							REVIEWERS_START_BRACKET, person.getLogin(),
							REVIEWERS_STOP_BRACKET) //
							: person.getLogin();

					result.add(new ContentProposal(proposalContent));
				}
			}

			return result.toArray(new IContentProposal[0]);
		}
	}
