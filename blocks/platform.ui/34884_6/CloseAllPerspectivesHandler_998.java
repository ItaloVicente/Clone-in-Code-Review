
package org.eclipse.ui.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.e4.ui.services.EContextService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

public class ActiveContextInfoHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final IWorkbenchPart part = HandlerUtil.getActivePartChecked(event);
		EContextService service = part.getSite()
				.getService(EContextService.class);
		for (String id : service.getActiveContextIds()) {
			System.out.println("activeContext: " + id); //$NON-NLS-1$
		}
		return null;
	}

}
