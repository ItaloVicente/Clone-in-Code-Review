package org.eclipse.egit.ui.internal.actions;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.egit.ui.internal.patch.PatchOperationUI;

public class CreatePatchActionHandler extends RepositoryActionHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		PatchOperationUI.createPatch(getPart(event), getRepository()).start();
		return null;
	}

	@Override
	public boolean isEnabled() {
		return getRepository() != null;
	}
}
