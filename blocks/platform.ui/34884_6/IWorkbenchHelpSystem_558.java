package org.eclipse.ui.help;

import org.eclipse.swt.events.HelpEvent;

@Deprecated
public interface IContextComputer {
    public Object[] computeContexts(HelpEvent event);

    public Object[] getLocalContexts(HelpEvent event);
}
