
package org.eclipse.ui.examples.rcp.browser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class BrowserPlugin extends AbstractUIPlugin {
    private static BrowserPlugin DEFAULT;
    
    public BrowserPlugin() {
        DEFAULT = this;
    }

    public static BrowserPlugin getDefault() {
        return DEFAULT;
    }

    public void log(Throwable t) {
        String msg = t.getMessage();
        if (msg == null)
            msg = t.toString();
        IStatus status = new Status(IStatus.ERROR, getBundle().getSymbolicName(), 0, msg, t);
        getLog().log(status);
    }
    
    public static List getNonSecondaryParts(IWorkbenchPage page) {
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(page.getViewReferences()));
        list.addAll(Arrays.asList(page.getEditorReferences()));
        for (Iterator i = list.iterator(); i.hasNext();) {
            IWorkbenchPartReference ref = (IWorkbenchPartReference) i.next();
            if (ref instanceof ISecondaryPart) {
                i.remove();
            }
        }
        return list;
    }
    
}
