package org.eclipse.egit.ui.internal.actions;

public class ConfigureFetchAction extends RepositoryAction {

	public ConfigureFetchAction() {
		super(ActionCommands.CONFIGURE_FETCH, new ConfigureFetchActionHandler());
	}
}
