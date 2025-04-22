package org.eclipse.ui.contexts;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.internal.util.Util;

@Deprecated
public final class EnabledSubmission implements Comparable {

    private final String activePartId;

    private final Shell activeShell;

    private final IWorkbenchPartSite activeWorkbenchPartSite;

    private final String contextId;

    private transient String string = null;

    public EnabledSubmission(String activePartId, Shell activeShell,
            IWorkbenchPartSite activeWorkbenchPartSite, String contextId) {
        if (contextId == null) {
			throw new NullPointerException();
		}

        this.activePartId = activePartId;
        this.activeShell = activeShell;
        this.activeWorkbenchPartSite = activeWorkbenchPartSite;
        this.contextId = contextId;
    }

    @Override
	public int compareTo(Object object) {
        EnabledSubmission castedObject = (EnabledSubmission) object;
        int compareTo = Util.compare(activeWorkbenchPartSite,
                castedObject.activeWorkbenchPartSite);

        if (compareTo == 0) {
            compareTo = Util.compare(activePartId, castedObject.activePartId);

            if (compareTo == 0) {
                compareTo = Util.compare(activeShell, castedObject.activeShell);

                if (compareTo == 0) {
					compareTo = Util.compare(contextId, castedObject.contextId);
				}
            }
        }

        return compareTo;
    }

    public String getActivePartId() {
        return activePartId;
    }

    public Shell getActiveShell() {
        return activeShell;
    }

    public IWorkbenchPartSite getActiveWorkbenchPartSite() {
        return activeWorkbenchPartSite;
    }

    public String getContextId() {
        return contextId;
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
            stringBuffer.append(",contextId="); //$NON-NLS-1$
            stringBuffer.append(contextId);
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }
}
