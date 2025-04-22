package org.eclipse.egit.ui.internal.fetch;

import org.eclipse.egit.core.internal.hosts.GitHosts.ServerType;
import org.eclipse.egit.ui.internal.UIText;

public enum GitServer {

	GITHUB(ServerType.GITHUB) {

		@Override
		public String getName() {
			return "Github"; //$NON-NLS-1$
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
			return UIText.GitServer_WizardTitleGithub;
		}
	},

	GITLAB(ServerType.GITLAB) {

		@Override
		public String getName() {
			return "Gitlab"; //$NON-NLS-1$
		}

		@Override
		public String getProposalLabel() {
			return UIText.GitServer_MergeRequestContentAssistLabel;
		}

		@Override
		public String getBranchName() {
			return UIText.GitServer_MergeRequestRefNameSuggestion;
		}

		@Override
		public String getChangeLabel() {
			return UIText.GitServer_MergeRequestLabel;
		}

		@Override
		public String getChangeNameSingular() {
			return UIText.GitServer_MergeRequestSingular;
		}

		@Override
		public String getChangeNamePlural() {
			return UIText.GitServer_MergeRequestPlural;
		}

		@Override
		public String getWizardTitle() {
			return UIText.GitServer_WizardTitleGitlab;
		}
	};

	private final ServerType serverType;

	private GitServer(ServerType serverType) {
		this.serverType = serverType;
	}

	public ServerType getType() {
		return serverType;
	}

	public abstract String getName();

	public abstract String getProposalLabel();

	public abstract String getBranchName();

	public abstract String getChangeLabel();

	public abstract String getChangeNameSingular();

	public abstract String getChangeNamePlural();

	public abstract String getWizardTitle();
}
