	public static final void addRefContentProposalToText(Text textField,
			Repository repository,
			IContentProposalCandidateProvider<Ref> refListProvider) {
		UIUtils.<Ref> addContentProposalToText(textField,
				refListProvider, (pattern, ref) -> {
					String shortenedName = Repository
							.shortenRefName(ref.getName());
					if (pattern != null
							&& !pattern.matcher(ref.getName()).matches()
							&& !pattern.matcher(shortenedName).matches()) {
						return null;
					}
					return new RefContentProposal(repository, ref);
				}, UIText.UIUtils_StartTypingForRemoteRefMessage,
				UIText.UIUtils_PressShortcutForRemoteRefMessage);
	}

	public static final <T> void addContentProposalToText(Text textField,
			IContentProposalCandidateProvider<T> candidateProvider,
			IContentProposalFactory<T> factory, String startTypingMessage,
			String shortcutMessage) {
