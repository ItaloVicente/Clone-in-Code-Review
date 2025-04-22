
package org.eclipse.ui.examples.contributions.view;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.examples.contributions.model.Person;
import org.eclipse.ui.handlers.HandlerUtil;

public class SwapInfoHandler extends AbstractHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		InfoView view = (InfoView) HandlerUtil.getActivePartChecked(event);
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) sel;
			if (selection.size() != 2) {
				return null;
			}
			Iterator i = selection.iterator();
			Person p1 = (Person) i.next();
			Person p2 = (Person) i.next();
			view.swap(p1, p2);
		}
		return null;
	}

}
