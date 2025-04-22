package org.eclipse.egit.ui.internal.actions;

public class ConfigurePushAction extends RepositoryAction {

	public ConfigurePushAction() {
		super(ActionCommands.CONFIGURE_PUSH, new ConfigurePushActionHandler());
	}
}
