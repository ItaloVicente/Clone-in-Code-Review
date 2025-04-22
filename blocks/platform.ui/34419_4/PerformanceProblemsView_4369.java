
package org.eclipse.ui.tests.performance.parts;

import java.util.StringTokenizer;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class PerformanceEditorPart extends EditorPart {

    private static final String PARAM_OUTLINE = "outline";
    
    private boolean dirty;
    private Label control;
    private boolean useOutline = false;

    public PerformanceEditorPart() {
        super();
    }

    public void doSave(IProgressMonitor monitor) {
    }

    public void doSaveAs() {
    }

    public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        super.setSite(site);
        super.setInput(input);
    }

    public boolean isDirty() {
        return dirty;
    }
    
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isSaveAsAllowed() {        
        return false;
    }

    public void createPartControl(Composite parent) {
        control = new Label(parent, SWT.NONE);

    }

    public void setFocus() {
        control.setFocus();
    }

    public void setInitializationData(IConfigurationElement cfig,
            String propertyName, Object data) {
        super.setInitializationData(cfig, propertyName, data);
        
        if (data instanceof String) {
           for (StringTokenizer toker = new StringTokenizer((String) data, ","); toker.hasMoreTokens(); ) {
               String token = toker.nextToken();
               if (token.equals(PARAM_OUTLINE))
                   useOutline = true;
           }
           
        }        
    }
    public Object getAdapter(Class adapter) {
        Object object = null;
        if (useOutline && adapter.equals(IContentOutlinePage.class)) {
            object = new ContentOutlinePage() {
            };
        }
        if (object != null)
            return object;
        return super.getAdapter(adapter);
    }
}
