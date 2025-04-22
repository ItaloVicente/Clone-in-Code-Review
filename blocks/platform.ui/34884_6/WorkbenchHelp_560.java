package org.eclipse.ui.help;

import java.util.ArrayList;

import org.eclipse.core.runtime.Assert;
import org.eclipse.help.IContext;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IViewPart;

@Deprecated
public class ViewContextComputer implements IContextComputer {
    private IViewPart view;

    private ArrayList contextList;

    private Object context;

    public ViewContextComputer(IViewPart viewPart, Object helpContext) {
        Assert.isTrue(helpContext instanceof String
                || helpContext instanceof IContext);
        view = viewPart;
        context = helpContext;
    }

    private void addContexts(Object object, HelpEvent event) {
        Assert.isTrue(object instanceof Object[]
                || object instanceof IContextComputer
                || object instanceof String);

        if (object instanceof String) {
            contextList.add(object);
            return;
        }

        Object[] contexts;
        if (object instanceof IContextComputer) {
            contexts = ((IContextComputer) object).getLocalContexts(event);
		} else {
			contexts = (Object[]) object;
		}

        for (int i = 0; i < contexts.length; i++) {
			contextList.add(contexts[i]);
		}
    }

    private void addContextsForControl(Control control, HelpEvent event) {
        Object object = WorkbenchHelp.getHelp(control);

        if (object == null || object == this) {
            return;
		}

        addContexts(object, event);
    }

    @Override
	public Object[] computeContexts(HelpEvent event) {
        contextList = new ArrayList();

        contextList.add(context);

        addContextsForControl(view.getSite().getShell(), event);

        return contextList.toArray();
    }

    @Override
	public Object[] getLocalContexts(HelpEvent event) {
        return new Object[] { context };
    }
}
