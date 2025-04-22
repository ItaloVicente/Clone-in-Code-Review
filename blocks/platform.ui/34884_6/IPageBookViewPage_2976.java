package org.eclipse.ui.part;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;

public interface IPage {
    public void createControl(Composite parent);

    public void dispose();

    public Control getControl();

    public void setActionBars(IActionBars actionBars);

    public void setFocus();
}
