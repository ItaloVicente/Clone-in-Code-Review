package org.eclipse.ui.internal.progress;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Control;

public class ProgressViewerLabelProvider extends LabelProvider {
    private Control control;

    @Override
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
