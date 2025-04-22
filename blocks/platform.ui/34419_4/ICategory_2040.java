package org.eclipse.ui.commands;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.internal.util.Util;

@Deprecated
@SuppressWarnings("all")
public final class HandlerSubmission implements Comparable {

    private final String activePartId;

    private final Shell activeShell;

    private final IWorkbenchPartSite activeWorkbenchPartSite;

    private final String commandId;

    private final IHandler handler;

    private final Priority priority;

    private transient String string;

	@Deprecated
    public HandlerSubmission(String activePartId, Shell activeShell,
            IWorkbenchPartSite activeWorkbenchPartSite, String commandId,
            IHandler handler, Priority priority) {
        if (commandId == null || handler == null || priority == null) {
			throw new NullPointerException();
		}

        this.activePartId = activePartId;
        this.activeShell = activeShell;
        this.activeWorkbenchPartSite = activeWorkbenchPartSite;
        this.commandId = commandId;
        this.handler = handler;
        this.priority = priority;
    }

	@Override
	@Deprecated
    public int compareTo(Object object) {
        HandlerSubmission castedObject = (HandlerSubmission) object;
        int compareTo = Util.compare(activeWorkbenchPartSite,
                castedObject.activeWorkbenchPartSite);

        if (compareTo == 0) {
            compareTo = Util.compare(activePartId, castedObject.activePartId);

            if (compareTo == 0) {
                compareTo = Util.compare(activeShell, castedObject.activeShell);

                if (compareTo == 0) {
                    compareTo = Util.compare(priority, castedObject.priority);

                    if (compareTo == 0) {
                        compareTo = Util.compare(commandId,
                                castedObject.commandId);

                        if (compareTo == 0) {
							compareTo = Util.compare(handler,
                                    castedObject.handler);
						}
                    }
                }
            }
        }

        return compareTo;
    }

	@Deprecated
    public String getActivePartId() {
        return activePartId;
    }

	@Deprecated
    public Shell getActiveShell() {
        return activeShell;
    }

	@Deprecated
    public IWorkbenchPartSite getActiveWorkbenchPartSite() {
        return activeWorkbenchPartSite;
    }

	@Deprecated
    public String getCommandId() {
        return commandId;
    }

	@Deprecated
    public IHandler getHandler() {
        return handler;
    }

	@Deprecated
    public Priority getPriority() {
        return priority;
    }

    @Override
	public String toString() {
        if (string == null) {
            final StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[activePartId="); //$NON-NLS-1$
            stringBuffer.append(activePartId);
            stringBuffer.append(",activeShell="); //$NON-NLS-1$
            stringBuffer.append(activeShell);
            stringBuffer.append(",activeWorkbenchSite="); //$NON-NLS-1$
            stringBuffer.append(activeWorkbenchPartSite);
            stringBuffer.append(",commandId="); //$NON-NLS-1$
            stringBuffer.append(commandId);
            stringBuffer.append(",handler="); //$NON-NLS-1$
            stringBuffer.append(handler);
            stringBuffer.append(",priority="); //$NON-NLS-1$
            stringBuffer.append(priority);
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }
}
