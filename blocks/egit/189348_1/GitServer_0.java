	},

	GITEA(ServerType.GITEA) {

		@Override
		public String getName() {
			return "Gitea"; //$NON-NLS-1$
		}

		@Override
		public String getProposalLabel() {
			return UIText.GitServer_PullRequestContentAssistLabel;
		}

		@Override
		public String getBranchName() {
			return UIText.GitServer_PullRequestRefNameSuggestion;
		}

		@Override
		public String getChangeLabel() {
			return UIText.GitServer_PullRequestLabel;
		}

		@Override
		public String getChangeNameSingular() {
			return UIText.GitServer_PullRequestSingular;
		}

		@Override
		public String getChangeNamePlural() {
			return UIText.GitServer_PullRequestPlural;
		}

		@Override
		public String getWizardTitle() {
			return UIText.GitServer_WizardTitleGitea;
		}
