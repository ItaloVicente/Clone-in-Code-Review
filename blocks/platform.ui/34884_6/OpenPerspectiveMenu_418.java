package org.eclipse.ui.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.util.Util;

public final class OpenPerspectiveAction extends Action implements
        IPluginContribution {

    private final PerspectiveMenu callback;

    private final IPerspectiveDescriptor descriptor;

    public OpenPerspectiveAction(final IWorkbenchWindow window,
            final IPerspectiveDescriptor descriptor,
            final PerspectiveMenu callback) {
        super(Util.ZERO_LENGTH_STRING);

        this.descriptor = descriptor;
        this.callback = callback;

        final String label = descriptor.getLabel();
        setText(label);
        setToolTipText(label);
        setImageDescriptor(descriptor.getImageDescriptor());

        window.getWorkbench().getHelpSystem().setHelp(this,
                IWorkbenchHelpContextIds.OPEN_PERSPECTIVE_ACTION);
    }

  
    @Override
	public final void runWithEvent(final Event event) {
        callback.run(descriptor, new SelectionEvent(event));
    }

    @Override
	public String getLocalId() {
        return descriptor.getId();
    }

    @Override
	public String getPluginId() {
        return descriptor instanceof IPluginContribution ? ((IPluginContribution) descriptor)
                .getPluginId()
                : null;
    }
}
