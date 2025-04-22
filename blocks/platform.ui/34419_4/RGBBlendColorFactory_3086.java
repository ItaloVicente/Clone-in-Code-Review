package org.eclipse.ui.themes;

import org.eclipse.swt.widgets.Composite;

public interface IThemePreview {

    void createControl(Composite parent, ITheme currentTheme);

    void dispose();
}
