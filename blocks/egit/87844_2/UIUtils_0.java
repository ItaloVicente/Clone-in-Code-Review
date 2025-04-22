		public Collection<? extends T> getCandidates();
	}

	public interface IContentProposalFactory<T> {

		public IContentProposal getProposal(Pattern pattern, T element);
