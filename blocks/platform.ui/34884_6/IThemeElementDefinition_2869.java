package org.eclipse.ui.internal.themes;

import java.util.Map;

public interface IThemeDescriptor extends IThemeElementDefinition {
    public static final String TAB_BORDER_STYLE = "TAB_BORDER_STYLE"; //$NON-NLS-1$

    public ColorDefinition[] getColors();

    public FontDefinition[] getFonts();

    public Map getData();
}
