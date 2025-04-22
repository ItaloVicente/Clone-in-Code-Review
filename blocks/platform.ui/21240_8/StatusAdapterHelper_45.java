package org.eclipse.e4.ui.progress.internal;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Control;

public class ProgressViewerLabelProvider extends LabelProvider {
    private Control control;

    public String getText(Object element) {
        JobTreeElement info = (JobTreeElement) element;
        return ProgressManagerUtil.shortenText(
                info.getCondensedDisplayString(), control);
    }

    public ProgressViewerLabelProvider(Control progressControl) {
        super();
        control = progressControl;
    }
}
