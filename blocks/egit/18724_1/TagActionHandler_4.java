package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.push.PushTagsWizard;
import org.eclipse.jgit.lib.Repository;

public class PushTagsActionHandler extends RepositoryActionHandler {
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(true, event);

		PushTagsWizard.openWizardDialog(repository);

		return null;
	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null;
	}
}
