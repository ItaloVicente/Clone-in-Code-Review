package org.eclipse.ui.themes;

import java.util.Set;

import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.util.IPropertyChangeListener;

public interface ITheme {

    void addPropertyChangeListener(IPropertyChangeListener listener);

    void dispose();

    boolean getBoolean(String key);

    ColorRegistry getColorRegistry();

    FontRegistry getFontRegistry();

    String getId();

    public int getInt(String key);

    String getLabel();

    String getString(String key);

    Set keySet();

    void removePropertyChangeListener(IPropertyChangeListener listener);
}
