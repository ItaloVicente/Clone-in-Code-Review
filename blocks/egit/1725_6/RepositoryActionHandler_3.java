package org.eclipse.egit.ui.internal.actions;

public class PullFromUpstreamConfigAction extends RepositoryAction {
	public PullFromUpstreamConfigAction() {
		super(ActionCommands.PULL_FROM_UPSTREAM_CONFIG,
				new PullFromUpstreamActionHandler());
	}
}
