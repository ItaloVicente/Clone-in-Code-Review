package org.eclipse.ui.themes;

import org.eclipse.jface.util.IPropertyChangeListener;

public interface IThemeManager {

    public static final String CHANGE_CURRENT_THEME = "CHANGE_CURRENT_THEME"; //$NON-NLS-1$

    public static final String DEFAULT_THEME = "org.eclipse.ui.defaultTheme"; //$NON-NLS-1$ 

    void addPropertyChangeListener(IPropertyChangeListener listener);

    ITheme getCurrentTheme();

    ITheme getTheme(String id);

    void removePropertyChangeListener(IPropertyChangeListener listener);

    void setCurrentTheme(String id);
}
