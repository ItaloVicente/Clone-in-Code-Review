
package org.eclipse.ui.examples.contributions.view;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.handlers.HandlerUtil;

public class RefreshInfoHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		InfoView view = (InfoView) HandlerUtil.getActivePartChecked(event);
		view.refresh();
		return null;
	}

}
